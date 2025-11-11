package src.tests;

import src.file_reader.FileEntityReader;
import src.models.ComparableEntity;
import src.models.Student;
import src.models.Teacher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileEntityReaderTest {

    public static void main(String[] args) throws IOException {
        testValidFile();
        testInvalidStudentSkipped();
        testEmptyFile();
        System.out.println("FileEntityReader работает корректно");
    }

    private static void testValidFile() throws IOException {
        String fileContent = """
                Учитель
                Имя: Илья
                Фамилия: Иванов
                id: 0
                Предмет: История
                Стаж: 10

                Ученик
                Имя: Мария
                Фамилия: Сидорова
                id: 1
                Класс: 5
                Возраст: 14
                """;

        Path tempFile = createTempFile(fileContent);

        List<ComparableEntity> list = FileEntityReader.readFromFile(tempFile.toString());

        if (list.isEmpty())
            throw new AssertionError("Ожидали непустой список");

        if (list.size() != 2)
            throw new AssertionError("Ожидали 2 объекта, получили " + list.size());

        if (!(list.get(0) instanceof Teacher))
            throw new AssertionError("Первый объект должен быть Teacher");

        if (!(list.get(1) instanceof Student))
            throw new AssertionError("Второй объект должен быть Student");

        Files.deleteIfExists(tempFile);
    }

    private static void testInvalidStudentSkipped() throws IOException {
        String fileContent = """
                Учитель
                Имя: Илья
                Фамилия: Иванов
                id: 0
                Предмет: История
                Стаж: 10

                Ученик
                Имя: Мария
                Фамилия: Сидорова
                id: 1
                Класс: 123
                Возраст: 14

                Учитель
                Имя: Анна
                Фамилия: Кузнецова
                id: 2
                Предмет: География
                Стаж: 5
                """;

        Path tempFile = createTempFile(fileContent);

        List<ComparableEntity> list = FileEntityReader.readFromFile(tempFile.toString());

        if (list.size() != 2)
            throw new AssertionError("Невалидный ученик должен быть пропущен. Получено объектов: " + list.size());

        if (!(list.get(0) instanceof Teacher && list.get(1) instanceof Teacher))
            throw new AssertionError("Ожидали двух учителей, получили: " + list);

        Files.deleteIfExists(tempFile);
    }

    private static void testEmptyFile() throws IOException {
        Path tempFile = createTempFile("");

        List<ComparableEntity> list = FileEntityReader.readFromFile(tempFile.toString());

        if (!list.isEmpty())
            throw new AssertionError("Пустой файл должен возвращать пустой список");

        Files.deleteIfExists(tempFile);
    }

    private static Path createTempFile(String content) throws IOException {
        Path tempFile = Files.createTempFile("entities_", ".txt");
        Files.writeString(tempFile, content);
        return tempFile;
    }
}
