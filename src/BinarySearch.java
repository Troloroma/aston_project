package src;

public class BinarySearch {

    //Используем дженерик T
    public static <T extends Comparable<T>> int binarySearch(T[] arr, T target) {
        int left = 0; // Левая граница области поиска
        int right = arr.length - 1; // Правая граница области поиска

        while (left <= right) {
            // Находим средний индекс.
            // Используем >>> 1 для предотвращения переполнения при больших значениях
            int mid = left + (right - left) / 2;

            int comparisonResult = arr[mid].compareTo(target);

            if (comparisonResult == 0) {
                return mid; // Элементы равны
            } else if (comparisonResult < 0) {
                left = mid + 1; // arr[mid] меньше, ищем справа
            } else {
                right = mid - 1; // arr[mid] больше, ищем слева
            }
        }

        // Если элемент не найден после завершения цикла, возвращаем -1
        return -1;
    }
}
