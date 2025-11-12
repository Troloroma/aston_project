package src.tests;

import src.io.IoConsole;
import src.models.ComparableEntity;
import src.models.Student;
import src.models.Teacher;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public class IoConsoleSimpleTest {
    private static int testCount = 0;
    private static int passedCount = 0;
    private final InputStream originalSystemIn = System.in;

    public static void main(String[] args) {
        IoConsoleSimpleTest tester = new IoConsoleSimpleTest();

        System.out.println("=== Запуск тестов IoConsole ===\n");

        tester.testReadStudentsFromConsole();
        tester.testReadTeachersFromConsole();
        tester.testReadMixedEntitiesFromConsole();
        tester.testReadEmptyCollection();
        tester.testStudentValidation();

        System.out.println("\n=== Результаты тестов ===");
        System.out.println("Пройдено: " + passedCount + " из " + testCount + " тестов");

        if (passedCount == testCount) {
            System.out.println("ВСЕ ТЕСТЫ ПРОЙДЕНЫ!");
        } else {
            System.out.println("НЕКОТОРЫЕ ТЕСТЫ ПРОВАЛЕНЫ");
        }
    }

    private void restoreSystemIn() {
        System.setIn(originalSystemIn);
    }

    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    private void assertTrue(boolean condition, String message) {
        testCount++;
        if (condition) {
            passedCount++;
            System.out.println("Yes " + message);
        } else {
            System.out.println("NO  " + message);
        }
    }

    private void assertEquals(Object expected, Object actual, String message) {
        testCount++;
        boolean condition = (expected == null && actual == null) ||
                (expected != null && expected.equals(actual));
        if (condition) {
            passedCount++;
            System.out.println("YES " + message + " (ожидалось: " + expected + ", получено: " + actual + ")");
        } else {
            System.out.println("NO " + message + " (ожидалось: " + expected + ", получено: " + actual + ")");
        }
    }

    private void assertNotNull(Object object, String message) {
        testCount++;
        if (object != null) {
            passedCount++;
            System.out.println("YES " + message);
        } else {
            System.out.println("NO " + message);
        }
    }

    public void testReadStudentsFromConsole() {
        System.out.println("\n--- Тест: чтение студентов из консоли ---");

        String input = "1\n" + // Выбор типа коллекции: ученики
                "Иван\n" + // Имя первого студента
                "Иванов\n" + // Фамилия первого студента
                "18\n" + // Возраст первого студента
                "9\n" + // Класс первого студента
                "1\n" + // ID первого студента
                "Мария\n" + // Имя второго студента
                "Петрова\n" + // Фамилия второго студента
                "19\n" + // Возраст второго студента
                "8\n" + // Класс второго студента
                "2\n"; // ID второго студента

        provideInput(input);

        try {
            List<ComparableEntity> students = IoConsole.readCollectionFromConsole(2);

            assertNotNull(students, "Список студентов не должен быть null");
            assertEquals(2, students.size(), "Должно быть 2 студента");

            // Проверка первого студента
            assertTrue(students.get(0) instanceof Student, "Первый элемент должен быть Student");
            Student firstStudent = (Student) students.get(0);
            assertEquals("Иван", firstStudent.getFirstName(), "Имя первого студента");
            assertEquals("Иванов", firstStudent.getLastName(), "Фамилия первого студента");
            assertEquals(18, firstStudent.getAge(), "Возраст первого студента");
            assertEquals(9, firstStudent.getGrade(), "Класс первого студента");
            assertEquals(1, firstStudent.getId(), "ID первого студента");

            // Проверка второго студента
            assertTrue(students.get(1) instanceof Student, "Второй элемент должен быть Student");
            Student secondStudent = (Student) students.get(1);
            assertEquals("Мария", secondStudent.getFirstName(), "Имя второго студента");
            assertEquals("Петрова", secondStudent.getLastName(), "Фамилия второго студента");
            assertEquals(19, secondStudent.getAge(), "Возраст второго студента");
            assertEquals(8, secondStudent.getGrade(), "Класс второго студента");
            assertEquals(2, secondStudent.getId(), "ID второго студента");

        } catch (Exception e) {
            System.out.println("Исключение во время теста: " + e.getMessage());
            e.printStackTrace();
        } finally {
            restoreSystemIn();
        }
    }

    public void testReadTeachersFromConsole() {
        System.out.println("\n--- Тест: чтение учителей из консоли ---");

        String input = "2\n" + // Выбор типа коллекции: учителя
                "Анна\n" + // Имя первого учителя
                "Сидорова\n" + // Фамилия первого учителя
                "ALGEBRA\n" + // Предмет первого учителя
                "10\n" + // Опыт первого учителя
                "1\n" + // ID первого учителя
                "Петр\n" + // Имя второго учителя
                "Орлов\n" + // Фамилия второго учителя
                "PHYSICS\n" + // Предмет второго учителя
                "15\n" + // Опыт второго учителя
                "2\n"; // ID второго учителя

        provideInput(input);

        try {
            List<ComparableEntity> teachers = IoConsole.readCollectionFromConsole(2);

            assertNotNull(teachers, "Список учителей не должен быть null");
            assertEquals(2, teachers.size(), "Должно быть 2 учителя");

            // Проверка первого учителя
            assertTrue(teachers.get(0) instanceof Teacher, "Первый элемент должен быть Teacher");
            Teacher firstTeacher = (Teacher) teachers.get(0);
            assertEquals("Анна", firstTeacher.getFirstName(), "Имя первого учителя");
            assertEquals("Сидорова", firstTeacher.getLastName(), "Фамилия первого учителя");
            assertEquals("Алгебра", firstTeacher.getSubject(), "Предмет первого учителя");
            assertEquals(10, firstTeacher.getExperience(), "Опыт первого учителя");
            assertEquals(1, firstTeacher.getId(), "ID первого учителя");

            // Проверка второго учителя
            assertTrue(teachers.get(1) instanceof Teacher, "Второй элемент должен быть Teacher");
            Teacher secondTeacher = (Teacher) teachers.get(1);
            assertEquals("Петр", secondTeacher.getFirstName(), "Имя второго учителя");
            assertEquals("Орлов", secondTeacher.getLastName(), "Фамилия второго учителя");
            assertEquals("Физика", secondTeacher.getSubject(), "Предмет второго учителя");
            assertEquals(15, secondTeacher.getExperience(), "Опыт второго учителя");
            assertEquals(2, secondTeacher.getId(), "ID второго учителя");

        } catch (Exception e) {
            System.out.println("Исключение во время теста: " + e.getMessage());
            e.printStackTrace();
        } finally {
            restoreSystemIn();
        }
    }

    public void testReadMixedEntitiesFromConsole() {
        System.out.println("\n--- Тест: чтение смешанной коллекции ---");

        String input = "3\n" + // Выбор типа коллекции: смешанная
                "1\n" + // Первый объект: ученик
                "Сергей\n" + // Имя ученика
                "Кузнецов\n" + // Фамилия ученика
                "16\n" + // Возраст ученика
                "10\n" + // Класс ученика
                "1\n" + // ID ученика
                "2\n" + // Второй объект: учитель
                "Елена\n" + // Имя учителя
                "Морозова\n" + // Фамилия учителя
                "HISTORY\n" + // Предмет учителя
                "8\n" + // Опыт учителя
                "2\n"; // ID учителя

        provideInput(input);

        try {
            List<ComparableEntity> entities = IoConsole.readCollectionFromConsole(2);

            assertNotNull(entities, "Список entities не должен быть null");
            assertEquals(2, entities.size(), "Должно быть 2 entity");

            // Проверка первого объекта (студент)
            assertTrue(entities.get(0) instanceof Student, "Первый элемент должен быть Student");
            Student student = (Student) entities.get(0);
            assertEquals("Сергей", student.getFirstName(), "Имя студента");
            assertEquals("Кузнецов", student.getLastName(), "Фамилия студента");

            // Проверка второго объекта (учитель)
            assertTrue(entities.get(1) instanceof Teacher, "Второй элемент должен быть Teacher");
            Teacher teacher = (Teacher) entities.get(1);
            assertEquals("Елена", teacher.getFirstName(), "Имя учителя");
            assertEquals("Морозова", teacher.getLastName(), "Фамилия учителя");
            assertEquals("История", teacher.getSubject(), "Предмет учителя");

        } catch (Exception e) {
            System.out.println("Исключение во время теста: " + e.getMessage());
            e.printStackTrace();
        } finally {
            restoreSystemIn();
        }
    }

    public void testReadEmptyCollection() {
        System.out.println("\n--- Тест: чтение пустой коллекции ---");

        String input = "1\n"; // Выбор типа коллекции

        provideInput(input);

        try {
            List<ComparableEntity> entities = IoConsole.readCollectionFromConsole(0);

            assertNotNull(entities, "Список не должен быть null даже при размере 0");
            assertTrue(entities.isEmpty(), "Список должен быть пустым");

        } catch (Exception e) {
            System.out.println("Исключение во время теста: " + e.getMessage());
            e.printStackTrace();
        } finally {
            restoreSystemIn();
        }
    }

    public void testStudentValidation() {
        System.out.println("\n--- Тест: валидация ввода студента ---");

        String input = "1\n" + // Выбор типа коллекции: ученики
                " \n" + // Пустое имя (должно запросить повторно)
                "Иван\n" + // Корректное имя
                " \n" + // Пустая фамилия (должно запросить повторно)
                "Иванов\n" + // Корректная фамилия
                "5\n" + // Возраст
                "6\n" + // Класс
                "1\n"; // ID

        provideInput(input);

        try {
            List<ComparableEntity> students = IoConsole.readCollectionFromConsole(1);

            assertNotNull(students, "Список студентов не должен быть null");
            assertEquals(1, students.size(), "Должен быть 1 студент");

            Student student = (Student) students.get(0);
            assertEquals("Иван", student.getFirstName(), "Имя студента после валидации");
            assertEquals("Иванов", student.getLastName(), "Фамилия студента после валидации");

        } catch (Exception e) {
            System.out.println("Исключение во время теста: " + e.getMessage());
            e.printStackTrace();
        } finally {
            restoreSystemIn();
        }
    }
}