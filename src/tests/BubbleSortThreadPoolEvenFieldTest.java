package src.tests;

import src.sorter.BubbleSortThreadPoolEvenField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BubbleSortThreadPoolEvenFieldTest {
	public static void main(String[] args) {
		testSortsOnlyEvenKeyedElements();
		System.out.println("BubbleSortThreadPoolEvenField работает");
	}

	private static void testSortsOnlyEvenKeyedElements() {
		List<Dummy> source = new ArrayList<>(Arrays.asList(
			new Dummy("odd-5", 5),
			new Dummy("even-8", 8),
			new Dummy("odd-7", 7),
			new Dummy("even-4", 4),
			new Dummy("even-6", 6),
			new Dummy("odd-9", 9)
		));

		BubbleSortThreadPoolEvenField<Dummy> sorter = new BubbleSortThreadPoolEvenField<>(Dummy::key);
		sorter.sort(source);

		assertSameObject(source.get(0), "odd-5");
		assertSameObject(source.get(2), "odd-7");
		assertSameObject(source.get(5), "odd-9");

		int[] evenKeys = {source.get(1).key(), source.get(3).key(), source.get(4).key()};
		if (!Arrays.equals(evenKeys, new int[]{4, 6, 8})) {
			throw new AssertionError("Чётные элементы должны отсортироваться: " + Arrays.toString(evenKeys));
		}
	}

	private static void assertSameObject(Dummy actual, String expectedId) {
		if (!actual.id().equals(expectedId)) {
			throw new AssertionError("Элемент с идентификатором " + expectedId + " должен остаться на месте, но сейчас " + actual.id());
		}
	}

	private static class Dummy implements Comparable<Dummy> {
		private final String id;
		private final int key;

		private Dummy(String id, int key) {
			this.id = id;
			this.key = key;
		}

		private String id() {
			return id;
		}

		private int key() {
			return key;
		}

		@Override
		public int compareTo(Dummy o) {
			return Integer.compare(this.key, o.key);
		}
	}
}

