package src.tests;

import src.models.Student;
import src.models.SubjectsEnum;
import src.models.Teacher;

public class ModelsCreationTest {
	public static void main(String[] args) {
		testStudentBuilder();
		testTeacherBuilder();
		System.out.println("Строители Student и Teacher работают");
	}

	private static void testStudentBuilder() {
		Student student = new Student.StudentBuilder()
				.firstName("Ирина")
				.lastName("Смирнова")
				.age(14)
				.grade(8)
				.id(42)
				.build();

		if (!"Ирина".equals(student.getFirstName())) {
			throw new AssertionError("Неверное имя студента: " + student.getFirstName());
		}

		if (!"Смирнова".equals(student.getLastName())) {
			throw new AssertionError("Неверная фамилия студента: " + student.getLastName());
		}

		if (student.getGrade() != 8) {
			throw new AssertionError("Неверный класс студента: " + student.getGrade());
		}

		if (student.getId() != 42) {
			throw new AssertionError("Неверный id студента: " + student.getId());
		}
	}

	private static void testTeacherBuilder() {
		Teacher teacher = new Teacher.TeacherBuilder()
				.firstName("Андрей")
				.lastName("Кузнецов")
				.subject(SubjectsEnum.HISTORY)
				.experience(15)
				.id(7)
				.build();

		if (!"Андрей".equals(teacher.getFirstName())) {
			throw new AssertionError("Неверное имя учителя: " + teacher.getFirstName());
		}

		if (!"Кузнецов".equals(teacher.getLastName())) {
			throw new AssertionError("Неверная фамилия учителя: " + teacher.getLastName());
		}

		if (!SubjectsEnum.HISTORY.toString().equals(teacher.getSubject())) {
			throw new AssertionError("Неверный предмет учителя: " + teacher.getSubject());
		}

		if (teacher.getExperience() != 15) {
			throw new AssertionError("Неверный опыт учителя: " + teacher.getExperience());
		}

		if (teacher.getId() != 7) {
			throw new AssertionError("Неверный id учителя: " + teacher.getId());
		}
	}
}


