package src;

import src.models.ComparableEntity;
import src.models.Student;
import src.models.Teacher;

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

            for (T person : dataList) {
                if (person instanceof Teacher t) {
                    out.println("Учитель");
                    out.println("Имя: " + t.getFirstName());
                    out.println("Фамилия: " + t.getLastName());
                    out.println("Предмет: " + t.getSubject());
                    out.println("Стаж: " + t.getExperience());
                    out.println("id: " + t.getId());
                    out.println(); // пустая строка между объектами
                } else if (person instanceof Student s) {
                    out.println("Ученик");
                    out.println("Имя: " + s.getFirstName());
                    out.println("Фамилия: " + s.getLastName());
                    out.println("Класс: " + s.getGrade());
                    out.println("Возраст: " + s.getAge());
                    out.println("id: " + s.getId());
                    out.println();
                }
            }

            out.println("--- Конец записи ---");
            System.out.println("Данные успешно добавлены в файл: " + filename);

        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл " + filename + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}