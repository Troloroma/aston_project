package src;

import java.util.List;

//Реализация паттерна Стратегия
//Универсальный интерфейс стратегии для любого типа данных T
public interface ISortStrategy<T extends Comparable<T>> {
	/**
	 * Метод для сортировки списка элементов типа T.
	 * @param t Список, который нужно отсортировать.
	 */
	void Sort(List<T> t);
}
