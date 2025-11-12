package src.counter;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class OccurrenceCounter {

    /**
     * Многопоточный подсчёт количества вхождений элемента N в коллекции.
     * @param collection коллекция для анализа
     * @param target элемент, количество вхождений которого нужно посчитать
     * @param <T> тип элементов
     * @return количество вхождений target
     */
    public static <T> long countOccurrences(List<T> collection, T target) {
        if (collection == null || collection.isEmpty()) {
            System.out.println("Коллекция пуста — считать нечего.");
            return 0;
        }

        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        int partSize = (int) Math.ceil((double) collection.size() / threads);
        List<Future<Long>> futures = new ArrayList<>();

        // Разделяем коллекцию на части и считаем в каждой
        for (int i = 0; i < collection.size(); i += partSize) {
            int from = i;
            int to = Math.min(i + partSize, collection.size());

            List<T> sublist = collection.subList(from, to);

            Callable<Long> task = () -> sublist.stream()
                    .filter(el -> Objects.equals(el, target))
                    .count();

            futures.add(executor.submit(task));
        }

        long totalCount = 0;
        for (Future<Long> f : futures) {
            try {
                totalCount += f.get();
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Ошибка при подсчёте: " + e.getMessage());
            }
        }

        executor.shutdown();

        System.out.printf("Элемент '%s' встречается в коллекции %d раз(а).%n", target, totalCount);
        return totalCount;
    }
}
