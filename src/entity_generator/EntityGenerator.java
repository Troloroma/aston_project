package src.entity_generator;

import src.models.*;
import java.util.*;

/**
 * Генератор случайных объектов {@link ComparableEntity}.
 * Создает коллекцию из случайных {@link Student} и {@link Teacher}.
 */
public class EntityGenerator {

    private static final List<String> FIRST_NAMES = List.of(
            "Иван", "Петр", "Мария", "Анна", "Сергей", "Елена", "Ольга",
            "Кирилл", "Никита", "Виктор", "Александра", "Екатерина"
    );

    private static final List<String> LAST_NAMES = List.of(
            "Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов",
            "Орлов", "Васильев", "Морозов", "Лебедев", "Горилов"
    );

    private final Random random = new Random();

    /**
     * Создает список случайных объектов (Teacher или Student).
     */
    public List<ComparableEntity> generateRandomPeople(int count) {
        List<ComparableEntity> entities = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            if (random.nextBoolean()) {
                entities.add(generateRandomTeacher(i));
            } else {
                entities.add(generateRandomStudent(i));
            }
        }
        return entities;
    }

    private Student generateRandomStudent(int id) {
        return new Student.StudentBuilder()
                .firstName(randomOf(FIRST_NAMES))
                .lastName(randomOf(LAST_NAMES))
                .age(7 + random.nextInt(12))      // 7–18
                .grade(1 + random.nextInt(11))    // 1–11
                .id(id)
                .build();
    }

    private Teacher generateRandomTeacher(int id) {
        return new Teacher.TeacherBuilder()
                .firstName(randomOf(FIRST_NAMES))
                .lastName(randomOf(LAST_NAMES))
                .subject(randomEnumValue(SubjectsEnum.class))
                .experience(random.nextInt(30))
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
