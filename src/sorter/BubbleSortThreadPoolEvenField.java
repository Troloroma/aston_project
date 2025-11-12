package src.sorter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.ToIntFunction;

/**
 *	Вариант пузырьковой сортировки на пуле потоков, который затрагивает только элементы
 *	с чётным значением произвольного числового поля, оставляя нечётные элементы на местах.
 */
public class BubbleSortThreadPoolEvenField<T extends Comparable<T>> implements ISortStrategy<T> {
	private static final int THREAD_COUNT = 2;
	private final ToIntFunction<? super T> keyExtractor;

	/**
	 *	@param keyExtractor	функция, возвращающая числовой признак, по которому решаем чётность и сравниваем.
	 */
	public BubbleSortThreadPoolEvenField(ToIntFunction<? super T> keyExtractor) {
		if (keyExtractor == null) {throw new IllegalArgumentException("keyExtractor не может быть null");}
		this.keyExtractor = keyExtractor;
	}

	@Override
	public void sort(List<T> list) {
		if (list == null || list.size() <= 1) {return;}

		List<Integer> evenIndices = new ArrayList<>();
		List<T> evenElements = new ArrayList<>();

		// Одним проходом собрали позиции и значения элементов с чётным ключом.
		for (int i = 0; i < list.size(); i++) {
			T element = list.get(i);
			if ((keyExtractor.applyAsInt(element) & 1) == 0) {
				evenIndices.add(i);
				evenElements.add(element);
			}
		}

		if (evenElements.size() <= 1) {return;}

		// Отдельно отсортировали только чётные элементы.
		sortEvenElements(evenElements);

		// Возвращаем отсортированные значения на исходные позиции, остальные элементы не трогаем.
		Iterator<T> sortedEven = evenElements.iterator();
		for (Integer idx : evenIndices) {
			list.set(idx, sortedEven.next());
		}
	}

	private void sortEvenElements(List<T> list) {
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
		int n = list.size();
		AtomicBoolean swapped = new AtomicBoolean(true);

		try {
			while (swapped.get()) {
				swapped.set(false);
				runPhase(executor, list, n, swapped, 0);
				runPhase(executor, list, n, swapped, 1);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			executor.shutdownNow();
			throw new RuntimeException("Сортировка прервана", e);
		} finally {
			executor.shutdown();
		}
	}

	private void runPhase(ExecutorService executor, List<T> list, int n, AtomicBoolean swapped, int parity) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
		AtomicReference<RuntimeException> phaseError = new AtomicReference<>();

		for (int worker = 0; worker < THREAD_COUNT; worker++) {
			final int startIndex = parity + worker * 2;
			if (startIndex >= n - 1) {
				latch.countDown();
				continue;
			}

			executor.submit(() -> {
				try {
					boolean localSwapped = false;
					for (int i = startIndex; i < n - 1; i += THREAD_COUNT * 2) {
						if (phaseError.get() != null) {return;}
						int leftKey = keyExtractor.applyAsInt(list.get(i));
						int rightKey = keyExtractor.applyAsInt(list.get(i + 1));
						// Сравнение ведём только по значению ключа, само значение в списке не трогаем.
						if (rightKey < leftKey) {
							T swap = list.get(i);
							list.set(i, list.get(i + 1));
							list.set(i + 1, swap);
							localSwapped = true;
						}
					}
					if (localSwapped) {swapped.set(true);}
				} catch (RuntimeException ex) {
					phaseError.compareAndSet(null, ex);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		RuntimeException pending = phaseError.get();
		if (pending != null) {throw pending;}
	}
}

