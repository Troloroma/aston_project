package src.tests;

import src.binary_search.BinarySearch;

public class BinarySearchTest {
	public static void main(String[] args) {
		Integer[] data = {1, 3, 5, 7, 9, 11, 13};
		int foundIndex = BinarySearch.binarySearch(data, 7);
		int notFoundIndex = BinarySearch.binarySearch(data, 4);

		if (foundIndex != 3) {
			throw new AssertionError("Ожидали индекс 3, получили " + foundIndex);
		}

		if (notFoundIndex != -1) {
			throw new AssertionError("Элемент отсутствует, но метод вернул " + notFoundIndex);
		}

		String[] words = {"apple", "banana", "kiwi"};
		int wordIndex = BinarySearch.binarySearch(words, "kiwi");

		if (wordIndex != 2) {
			throw new AssertionError("Строковой поиск вернул " + wordIndex);
		}

		System.out.println("BinarySearch работает");
	}
}

