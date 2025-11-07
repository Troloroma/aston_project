package src;

import java.util.List;

//Сортирует список элементов методом пузырьковой сортировки
public class BubbleSort<T extends Comparable<T>> implements ISortStrategy<T> {
	@Override
	public void Sort(List<T> t) {
		// Внешний цикл: количество проходов по списку
		for (int i = 0; i < t.size() - 1; i++) {
			// Внутренний цикл: сравнение соседних элементов
			// После каждого прохода наибольший элемент "всплывает" в конец,
			// поэтому уменьшаем границу на i (уже отсортированные элементы не проверяем)
			for(int j = 0; j < t.size() - i - 1; j++) {
				// Если текущий элемент больше следующего, меняем их местами
				if(t.get(j + 1).compareTo(t.get(j)) < 0) {
					// Обмен элементов через временную переменную
					T swap = t.get(j);
					t.set(j, t.get(j + 1));
					t.set(j + 1, swap);
				}
			}
		}
		System.out.println("Список отсортирован пузырьком.");
	}
}

