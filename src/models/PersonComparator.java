package src.models;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/***
 * При сравнении учителя и ученика, учитель на первом месте. Если сравниваются одинаковые классы, тогда уже идет сравнение по полям.
 * Сравнение будет по типу List<Person> people = new ArrayList<>(); people.sort(new PersonComparator());
 */

public class PersonComparator implements Comparator<Person> {

	/***
	 *  Компараторы для Teacher и Student.
	 *  Приоритет сравнений у Teacher: lastName, firstName, id, subject, experience
	 *  Приоритет сравнений у Student: lastName, firstName, id, grade, age
	 */
	private final Map<Class<?>, Comparator<?>> comparators = Map.of(
		Teacher.class, Comparator.comparing(Teacher::getLastName)
			.thenComparing(Teacher::getFirstName)
			.thenComparingInt(Teacher::getId)
			.thenComparing(Teacher::getSubject)
			.thenComparingInt(Teacher::getExperience),

		Student.class, Comparator.comparing(Student::getLastName)
			.thenComparing(Student::getFirstName)
			.thenComparingInt(Student::getId)
			.thenComparingInt(Student::getGrade)
			.thenComparingInt(Student::getAge)
	);

	private final List<Class<?>> typeOrder = List.of(
		Teacher.class, // сначала учителя
		Student.class // потом студенты
	);

	@Override
	public int compare(Person p1, Person p2) {
		// Сортировка по типу (Teacher → Student)
		int typeCompare = Integer.compare(
			typeOrder.indexOf(p1.getClass()),
			typeOrder.indexOf(p2.getClass())
		);
		if (typeCompare != 0) return typeCompare;

		// Если одного типа - выбирается компаратор из мапы
		Comparator<Person> cmp = getComparatorFor(p1.getClass());
		return cmp.compare(p1, p2);
	}

	private <T extends Person> Comparator<T> getComparatorFor(Class<?> clazz) {
		//noinspection unchecked
		return (Comparator<T>) comparators.getOrDefault(clazz, Comparator.comparing(Person::getLastName));
	}
}
