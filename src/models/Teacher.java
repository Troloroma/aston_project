package models;

/***
 * Use example:
 * List<Teacher> teachers = new ArrayList<>();
 *         teachers.add(new Teacher.TeacherBuilder()
 *                 .firstName("Мария")
 *                 .lastName("Смирнова")
 *                 .subject(SubjectsEnum.ALGEBRA)
 *                 .experience(10)
 *                 .build());
 */
public class Teacher implements Person {
    private final String firstName;
    private final String lastName;
    private final SubjectsEnum subject;
    private final int experience; // in years
    private final int id;

    private Teacher(TeacherBuilder builder) {
        if (builder == null) throw new IllegalArgumentException("Builder cannot be null");
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.subject = builder.subject != null ? builder.subject : SubjectsEnum.OTHER;
        this.experience = builder.experience;
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

    public String getSubject() {
        return subject.toString();
    }

    public int getExperience() {
        return experience;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Teacher [firstName=" + firstName +
                ", lastName=" + lastName +
                ", subject=" + subject +
                ", experience=" + experience +
                ", id=" + id + "]";
    }

    public static class TeacherBuilder {
        private String firstName;
        private String lastName;
        private SubjectsEnum subject;
        private int experience;
        private int id;

        public TeacherBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public TeacherBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public TeacherBuilder subject(SubjectsEnum subject) {
            this.subject = subject;
            return this;
        }

        public TeacherBuilder experience(int experience) {
            this.experience = experience;
            return this;
        }

        public TeacherBuilder id(int id) {
            this.id = id;
            return this;
        }

        public Teacher build() {
            if (!validateTeacher()) {
                throw new IllegalArgumentException("Some of the fields are invalid");
            }
            return new Teacher(this);
        }

        private boolean validateTeacher() {
            return (firstName != null && !firstName.trim().isEmpty()
                    && lastName != null && !lastName.trim().isEmpty()
                    && experience >= 0
                    && id >= 0);
        }
    }
}