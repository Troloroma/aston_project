package src;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DataWriter {

    /**
     * Записывает коллекцию объектов в файл в режиме добавления (append).
     * Каждый элемент списка записывается на новой строке.
     *
     * @param filename Имя файла для записи.
     * @param dataList Список данных для записи.
     */
    public static <T> void writeToFile(String filename, List<T> dataList) {
        // Используем PrintWriter и FileWriter с параметром 'true' для режима добавления (append)
        try (FileWriter fw = new FileWriter(filename, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println("--- Начало записи новой коллекции ---");
            for (T item : dataList) {
                out.println(item.toString());
            }
            out.println("--- Конец записи ---");
            System.out.println("Данные успешно добавлены в файл: " + filename);

        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл " + filename + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}