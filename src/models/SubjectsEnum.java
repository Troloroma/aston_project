package models;

public enum SubjectsEnum {
    ALGEBRA ("Algebra"),
    PE("Physical Education"),
    HISTORY("History"),
    PHYSICS("Physics"),
    BIOLOGY("Biology"),
    GEOGRAPHY("Geography"),
    GEOMETRY("Geometry"),
    OTHER("Other");

    private final String title;

    SubjectsEnum(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
