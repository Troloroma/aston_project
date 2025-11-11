package src.tests;

import src.entity_generator.EntityGenerator;
import src.models.ComparableEntity;
import src.models.Student;
import src.models.Teacher;

import java.util.List;

public class EntityGeneratorTest {
	public static void main(String[] args) {
		testGenerateRandomPeople();
		System.out.println("EntityGenerator работает");
	}

	private static void testGenerateRandomPeople() {
		EntityGenerator generator = new EntityGenerator();
		int count = 20;
		List<ComparableEntity> people = generator.generateRandomPeople(count);

		if (people == null) {
			throw new AssertionError("Генератор вернул null вместо списка");
		}

		if (people.size() != count) {
			throw new AssertionError("Ожидали список длиной " + count + ", получили " + people.size());
		}

		for (int i = 0; i < people.size(); i++) {
			ComparableEntity person = people.get(i);
			if (person == null) {
				throw new AssertionError("Элемент #" + i + " равен null");
			}

			if (person.getId() != i) {
				throw new AssertionError("Ожидали id " + i + ", получили " + person.getId());
			}

			if (person instanceof Student) {
				Student student = (Student) person;
				if (student.getGrade() < 1 || student.getGrade() > 11) {
					throw new AssertionError("Некорректный класс у ученика: " + student.getGrade());
				}
				if (student.getAge() < 7 || student.getAge() > 18) {
					throw new AssertionError("Некорректный возраст у ученика: " + student.getAge());
				}
			} else if (person instanceof Teacher) {
				Teacher teacher = (Teacher) person;
				if (teacher.getExperience() < 0) {
					throw new AssertionError("Опыт учителя не может быть отрицательным: " + teacher.getExperience());
				}
			} else {
				throw new AssertionError("Неизвестный тип сущности: " + person.getClass());
			}
		}
	}
}


