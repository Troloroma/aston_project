package src.models;

public enum SubjectsEnum {
	ALGEBRA ("Алгебра"),
	PE("Физ-ра"),
	HISTORY("История"),
	PHYSICS("Физика"),
	BIOLOGY("Биология"),
	GEOGRAPHY("География"),
	GEOMETRY("Геометрия"),
	OTHER("Другое");

	private final String title;

	SubjectsEnum(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return title;
	}
}
