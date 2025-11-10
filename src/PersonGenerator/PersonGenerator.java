package src.PersonGenerator;

import src.models.*;
import java.util.*;

/**
 * Генератор случайных объектов {@link Person}.
 * Создает коллекцию из случайных {@link Student} и {@link Teacher}.
 */
public class PersonGenerator {
    private static final List<String> FIRST_NAMES = List.of(
            "Иван", "Петр", "Мария", "Анна", "Сергей", "Елена", "Ольга", "Кирилл", "Никита", "Виктор", "Александра", "Екатерина",
            "Евангелина", "Кузя", "Даниил", "Денис"

    );
    private static final List<String> LAST_NAMES = List.of(
            "Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов", "Орлов", "Васильев", "Морозов", "Лебедев", "Горилов", "Тигрилов",
            "Зайцев", "Тоблер"
    );
    private final Random random = new Random();

    /**
     * Создает список людей случайного типа (учителя/студенты).
     * @param count количество элементов
     * @return список объектов Person
     */
    public List<Person> generateRandomPeople(int count) {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (random.nextBoolean()) {
                people.add(generateRandomTeacher(i));
            } else {
                people.add(generateRandomStudent(i));
            }
        }
        return people;
    }

    private Student generateRandomStudent(int id) {
        return new Student.StudentBuilder()
                .firstName(randomOf(FIRST_NAMES))
                .lastName(randomOf(LAST_NAMES))
                .age(7 + random.nextInt(12))      // 7–18 лет
                .grade(1 + random.nextInt(11))    // 1–11 класс
                .id(id)
                .build();
    }

    private Teacher generateRandomTeacher(int id) {
        return new Teacher.TeacherBuilder()
                .firstName(randomOf(FIRST_NAMES))
                .lastName(randomOf(LAST_NAMES))
                .subject(randomEnumValue(SubjectsEnum.class))
                .experience(random.nextInt(30))   // 0–29 лет опыта
                .id(id)
                .build();
    }

    private <T> T randomOf(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    private <E extends Enum<E>> E randomEnumValue(Class<E> enumClass) {
        E[] values = enumClass.getEnumConstants();
        return values[random.nextInt(values.length)];
    }
}
