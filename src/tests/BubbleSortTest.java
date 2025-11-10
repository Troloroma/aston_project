package src.tests;

import src.sorter.BubbleSort;
import java.util.Arrays;
import java.util.List;

public class BubbleSortTest {
	public static void main(String[] args) {
		BubbleSort<Integer> sorter = new BubbleSort<>();
		List<Integer> actual = Arrays.asList(5, 1, 4, 2, 8);
		List<Integer> expected = Arrays.asList(1, 2, 4, 5, 8);

		sorter.sort(actual);

		if (!actual.equals(expected)) {
			throw new AssertionError("Список отсортирован неверно: " + actual);
		}

		System.out.println("Ок");
	}
}
