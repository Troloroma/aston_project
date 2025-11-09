package src;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

//<T> — это дженерик-параметр (позволяет работать с разными типами данных)
public class BubbleSortThreadPool<T extends Comparable<T>> implements ISortStrategy<T> {
	private static final int THREAD_COUNT = 2;	// Минимум 2 потока

	@Override
	public void Sort(List<T> t) {
		//если нечего сортировать возвращааемся
        if (t == null || t.size() <= 1) {return;}

		// Создает пул из 2 потоков, которые будут выполнять задачи
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
		int n = t.size();	// Размер списка
		AtomicBoolean swapped = new AtomicBoolean(true);	// Флаг: были ли перестановки
        // AtomicBoolean нужен для безопасного доступа из разных потоков

		try {
			// Внешний цикл: повторяем фазы, пока хотя бы в одной фазе были перестановки
			while (swapped.get()) {
				swapped.set(false);
				// Фаза чётных индексов (odd-even sort): пары (0,1), (2,3) и т.д.
				runPhase(executor, t, n, swapped, 0);
				// Фаза нечётных индексов: пары (1,2), (3,4) и т.д.
				runPhase(executor, t, n, swapped, 1);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			executor.shutdownNow();
			throw new RuntimeException("Сортировка прервана", e);
		} finally {
			executor.shutdown();
		}

		System.out.println("Список отсортирован пузырьком в " + THREAD_COUNT + " потоках.");
	}

	private void runPhase(ExecutorService executor, List<T> list, int n, AtomicBoolean swapped, int parity) throws InterruptedException {
		// Счётчик завершения всех рабочих задач фазы
		CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
		// Храним первое исключение, если оно возникнет внутри любой задачи
		AtomicReference<RuntimeException> phaseError = new AtomicReference<>();

		for (int worker = 0; worker < THREAD_COUNT; worker++) {
			final int startIndex = parity + worker * 2;
			if (startIndex >= n - 1) {
				// Нечего делать для этого потока — сразу декрементируем счётчик
				latch.countDown();
				continue;
			}

			executor.submit(() -> {
				try {
					// Локальный флаг, чтобы не дергать AtomicBoolean без нужды
					boolean localSwapped = false;
					// Поток шагает по своей подпоследовательности с шагом THREAD_COUNT * 2
					for (int i = startIndex; i < n - 1; i += THREAD_COUNT * 2) {
						// Если уже поймали исключение — выходим пораньше
						if (phaseError.get() != null) {return;}
						if (list.get(i + 1).compareTo(list.get(i)) < 0) {
							// Поменяли местами текущую пару
							T swap = list.get(i);
							list.set(i, list.get(i + 1));
							list.set(i + 1, swap);
							localSwapped = true;
						}
					}
					// Если были перестановки — выставляем общий флаг
					if (localSwapped) {swapped.set(true);}
				} catch (RuntimeException ex) {
					// Запоминаем первое исключение, остальные игнорируем
					phaseError.compareAndSet(null, ex);
				} finally {
					// Сообщаем, что поток завершил фазу
					latch.countDown();
				}
			});
		}

		// Ждём завершения всех задач текущей фазы
		latch.await();
		RuntimeException pending = phaseError.get();
		// Если кто-то упал, пробрасываем исключение на вызывающий поток
		if (pending != null) {throw pending;}
	}
}
