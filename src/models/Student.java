package src.models;

/***
 * Use example:
 * List<Student> students = new ArrayList<>();
 *         students.add(new Student.StudentBuilder()
 *                 .firstName("Иван")
 *                 .lastName("Петров")
 *                 .age(16)
 *                 .grade(10)
 *                 .build());
 */
public class Student implements Person {
	private final String firstName;
	private final String lastName;
	private final int age;
	private final int grade; // from 1 to 11
	private final int id;

	public Student(StudentBuilder builder) {
		if (builder == null) throw new IllegalArgumentException("Builder cannot be null");
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.age = builder.age;
		this.grade = builder.grade;
		this.id = builder.id;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public int getId() {
		return id;
	}

	public int getAge() {
		return age;
	}

	public int getGrade() {
		return grade;
	}

	@Override
	public String toString() {
		return "Student [firstName=" + firstName +
				", lastName=" + lastName +
				", age=" + age +
				", grade=" + grade +
				", id=" + id + "]";
	}

	public static class StudentBuilder {
		private String firstName;
		private String lastName;
		private int age;
		private int grade;
		private int id;

		public StudentBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public StudentBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public StudentBuilder age(int age) {
			this.age = age;
			return this;
		}

		public StudentBuilder grade(int grade) {
			this.grade = grade;
			return this;
		}

		public StudentBuilder id(int id) {
			this.id = id;
			return this;
		}

		public Student build() {
			if (!validateStudent()) {
				throw new IllegalArgumentException("Some of the fields are invalid");
			}
			return new Student(this);
		}

		private boolean validateStudent() {
			return (firstName != null && !firstName.trim().isEmpty()
					&& lastName != null && !lastName.trim().isEmpty()
					&& age >= 0
					&& grade > 0 && grade <= 11
					&& id >= 0);
		}
	}
}
