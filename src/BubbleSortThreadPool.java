package src;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

//<T> — это дженерик-параметр (позволяет работать с разными типами данных)
public class BubbleSortThreadPool<T extends Comparable<T>> implements ISortStrategy<T> {
    private static final int THREAD_COUNT = 2;  // Минимум 2 потока
    @Override
    public void Sort(List<T> t) {
        //если нечего сортировать возвращааемся
        if (t == null || t.size() <= 1) {return;}

        // Создает пул из 2 потоков, которые будут выполнять задачи
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        int n = t.size();  // Размер списка
        AtomicBoolean swapped = new AtomicBoolean(true);  // Флаг: были ли перестановки
        // AtomicBoolean нужен для безопасного доступа из разных потоков

        try {
            while (swapped.get()){
                swapped.set(false);	//сброс флага
                CountDownLatch latch = new CountDownLatch(2);  // Ждем 2 потока

                //запуск первого потока
                executor.submit(()->{
                    try {
                        boolean localSwapped = false;  // Локальный флаг перестановок

                        // Обрабатываем пары: (0,1), (2,3), (4,5)...
                        for (int i = 0; i < n - 1; i += 2) {
                            synchronized (t) {  // Блокируем список для безопасного доступа
                                if (t.get(i + 1).compareTo(t.get(i)) < 0) {
                                    // Если следующий меньше текущего - меняем местами
                                    T swap = t.get(i);
                                    t.set(i, t.get(i + 1));
                                    t.set(i + 1, swap);
                                    localSwapped = true;
                                }
                            }
                        }

                        if (localSwapped) {
                            swapped.set(true);  // Были перестановки
                        }
                    } finally {
                        latch.countDown();  // Сигнализируем о завершении
                    }
                });

                //запуск второго потока
                executor.submit(() -> {  // Задача для второго потока
                    try {
                        boolean localSwapped = false;

                        // Обрабатываем пары: (1,2), (3,4), (5,6)...
                        for (int i = 1; i < n - 1; i += 2) {
                            synchronized (t) {
                                if (t.get(i + 1).compareTo(t.get(i)) < 0) {
                                    T swap = t.get(i);
                                    t.set(i, t.get(i + 1));
                                    t.set(i + 1, swap);
                                    localSwapped = true;
                                }
                            }
                        }

                        if (localSwapped) {
                            swapped.set(true);
                        }
                    } finally {
                        latch.countDown();  // Сигнализируем о завершении
                    }
                });
                latch.await();  // Ждем, пока оба потока завершат работу
                // Только после этого переходим к следующей итерации
            }
        //Обработка исключений
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Сортировка прервана", e);
        } finally {
            executor.shutdown();  //  закрываем пул потоков
        }

        System.out.println("Список отсортирован пузырьком в " + THREAD_COUNT + " потоках.");
    }

}
