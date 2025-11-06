package src;
 //Сортирует массив целых чисел методом пузырьковой сортировки
public class BubbleSort implements ISortStrategy{
	@Override
	public void Sort(int[] sortArr) {
		// Внешний цикл: количество проходов по массиву
		for (int i = 0; i < sortArr.length - 1; i++) {
			// Внутренний цикл: сравнение соседних элементов
			// После каждого прохода наибольший элемент "всплывает" в конец,
			// поэтому уменьшаем границу на i (уже отсортированные элементы не проверяем)
			for(int j = 0; j < sortArr.length - i - 1; j++) {
				// Если текущий элемент больше следующего, меняем их местами
				if(sortArr[j + 1] < sortArr[j]) {
					// Обмен элементов через временную переменную
					int swap = sortArr[j];
					sortArr[j] = sortArr[j + 1];
					sortArr[j + 1] = swap;
				}
			}
		}
		System.out.println("Массив отсортирован пузырьком.");
	}
}

