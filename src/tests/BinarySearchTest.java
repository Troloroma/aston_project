package src.tests;

import src.binary_search.BinarySearch;
import src.models.*;

import java.util.Arrays;
import java.util.List;

public class BinarySearchTest {
	public static void main(String[] args) {
		testListIdSearch();
		System.out.println("BinarySearch работает");
	}

	private static ComparableEntity[] fixtures() {
		return new ComparableEntity[]{
				new Student.StudentBuilder()
						.firstName("Иван")
						.lastName("Иванов")
						.age(15)
						.grade(9)
						.id(1)
						.build(),
				new Teacher.TeacherBuilder()
						.firstName("Мария")
						.lastName("Петрова")
						.subject(SubjectsEnum.ALGEBRA)
						.experience(12)
						.id(2)
						.build(),
				new Student.StudentBuilder()
						.firstName("Анна")
						.lastName("Сидорова")
						.age(13)
						.grade(7)
						.id(3)
						.build()
		};
	}

	private static void testListIdSearch() {
		List<ComparableEntity> entities = Arrays.asList(fixtures());
		int studentIndex = BinarySearch.binarySearchById(entities, 3);
		int missingIndex = BinarySearch.binarySearchById(entities, 0);

		if (studentIndex != 2) {
			throw new AssertionError("Ожидали индекс 2, получили " + studentIndex);
		}

		if (missingIndex != -1) {
			throw new AssertionError("Ожидали -1 для отсутствующего id, получили " + missingIndex);
		}
	}
}

