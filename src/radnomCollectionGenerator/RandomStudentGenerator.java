package src.radnomCollectionGenerator;

import src.models.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomStudentGenerator {

    private static final List<String> FIRST_NAMES = Arrays.asList(
            "Иван", "Петр", "Алексей", "Михаил", "Дмитрий", "Егор", "Сергей", "Николай", "Антон", "Андрей"
    );

    private static final List<String> LAST_NAMES = Arrays.asList(
            "Иванов", "Петров", "Сидоров", "Кузнецов", "Смирнов", "Попов", "Егоров", "Федоров", "Васильев", "Григорьев"
    );

    private static final Random random = new Random();
    private static int nextId = 1;

    public static Student generateRandomStudent() {
        String firstName = FIRST_NAMES.get(random.nextInt(FIRST_NAMES.size()));
        String lastName = LAST_NAMES.get(random.nextInt(LAST_NAMES.size()));
        int age = 7 + random.nextInt(11); // от 7 до 17 лет
        int grade = 1 + random.nextInt(11); // от 1 до 11 класса
        int id = nextId++;

        return new Student.StudentBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .grade(grade)
                .id(id)
                .build();
    }

    public static List<Student> generateStudents(int count) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            students.add(generateRandomStudent());
        }
        return students;
    }

    public static void main(String[] args) {
        List<Student> students = generateStudents(10);
        students.forEach(System.out::println);
    }
}
