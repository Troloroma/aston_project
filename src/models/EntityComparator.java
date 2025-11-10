package src.models;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/***
 * При сравнении учителя и ученика, учитель на первом месте. Если сравниваются одинаковые классы, тогда уже идет сравнение по полям.
 */

public class EntityComparator implements Comparator<ComparableEntity> {

    public static final Comparator<ComparableEntity> DEFAULT  = new EntityComparator();

    // Компараторы для конкретных классов
    private final Map<Class<?>, Comparator<?>> comparators = Map.of(
            Teacher.class, Comparator.comparing(Teacher::getId)
                    .thenComparing(Teacher::getLastName)
                    .thenComparing(Teacher::getFirstName)
                    .thenComparingInt(Teacher::getId)
                    .thenComparing(Teacher::getSubject)
                    .thenComparingInt(Teacher::getExperience),

            Student.class, Comparator.comparing(Student::getId)
                    .thenComparing(Student::getLastName)
                    .thenComparing(Student::getFirstName)
                    .thenComparingInt(Student::getGrade)
                    .thenComparingInt(Student::getAge)
    );

    // Сначала учителя, потом студенты
    private final List<Class<?>> typeOrder = List.of(
            Teacher.class,
            Student.class
    );

    @Override
    public int compare(ComparableEntity o1, ComparableEntity o2) {
        if (o1 == o2) return 0;
        Class<?> c1 = o1.getClass();
        Class<?> c2 = o2.getClass();

        int typeCompare = Integer.compare(typeIndex(c1), typeIndex(c2));
        if (typeCompare != 0) return typeCompare;

        Comparator<ComparableEntity> cmp = getComparatorFor(c1);
        return cmp.compare(o1, o2);
    }

    private Comparator<ComparableEntity> getComparatorFor(Class<?> clazz) {
        //noinspection unchecked
        return (Comparator<ComparableEntity>) comparators.getOrDefault(clazz,
                Comparator.comparing((ComparableEntity e) -> e.toString())
        );
    }

    private int typeIndex(Class<?> c) {
        int idx = typeOrder.indexOf(c);
        return idx >= 0 ? idx : Integer.MAX_VALUE; // неизвестные типыв конце
    }
}
