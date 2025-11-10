package src.io;

import src.models.ComparableEntity;
import src.models.Student;
import src.models.SubjectsEnum;
import src.models.Teacher;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static src.io.ConsoleUtils.*;

public class IoConsole {

    private IoConsole() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static List<ComparableEntity> readCollectionFromConsole(int size) {
        System.out.println("Выберите тип коллекции для заполнения:");
        System.out.println("1 - Ученики");
        System.out.println("2 - Учителя");
        System.out.println("3 - Ученики и учителя");

        int choice = readInt("Введите число (1-3): ", 1, 3);

        return switch (choice) {
            case 1 -> readStudents(size);
            case 2 -> readTeachers(size);
            default -> readEntities(size);
        };
    }

    // Заполнение List Student
    private static StudentList readStudents(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> {
                    System.out.println("\nЗаполняю ученика #" + (i + 1));
                    return readStudent();
                })
                .collect(Collectors.toCollection(StudentList::new));
    }

    private static Student readStudent() {
        String firstName = readLine("Введите имя ученика: ");
        String lastName = readLine("Введите фамилию ученика: ");
        int age = readInt("Введите возраст: ", 5, 25);
        int grade = readInt("Введите класс (1-11): ", 1, 11);
        int id = readInt("Введите ID: ", 0, Integer.MAX_VALUE);

        return new Student.StudentBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .grade(grade)
                .id(id)
                .build();
    }

    // Заполнение List Teacher
    private static TeacherList readTeachers(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> {
                    System.out.println("\nЗаполняю учителя #" + (i + 1));
                    return readTeacher();
                })
                .collect(Collectors.toCollection(TeacherList::new));
    }

    private static Teacher readTeacher() {
        String firstName = readLine("Введите имя учителя: ");
        String lastName = readLine("Введите фамилию учителя: ");
        System.out.println("Выберите предмет из списка:");
        Arrays.stream(SubjectsEnum.values()).forEach(s -> System.out.println(" - " + s));
        SubjectsEnum subject = readEnum("Введите предмет: ", SubjectsEnum.class);
        int experience = readInt("Введите опыт (в годах): ", 0, 60);
        int id = readInt("Введите ID: ", 0, Integer.MAX_VALUE);

        return new Teacher.TeacherBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .subject(subject)
                .experience(experience)
                .id(id)
                .build();
    }

    // Заполнение сразу всех
    private static EntityList readEntities(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> {
                    System.out.println("\nКого добавить под номером #" + (i + 1) + "?");
                    System.out.println("1 - Ученика");
                    System.out.println("2 - Учителя");
                    int choice = readInt("Введите число (1-2): ", 1, 2);

                    return choice == 1 ? readStudent() : readTeacher();
                })
                .collect(Collectors.toCollection(EntityList::new));
    }
}
