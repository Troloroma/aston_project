package src.tests;

import src.BubbleSortThreadPool;
import java.util.Arrays;
import java.util.List;

public class BubbleSortThreadPoolTest {
	public static void main(String[] args) {
		BubbleSortThreadPool<Integer> sorter = new BubbleSortThreadPool<>();
		List<Integer> actual = Arrays.asList(9, 3, 7, 1, 5, 2, 8, 4, 6);
		List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

		sorter.Sort(actual);

		if (!actual.equals(expected)) {
			throw new AssertionError("ThreadPool сортировал неверно: " + actual);
		}

		System.out.println("ThreadPool BubbleSort ок");
	}
}

