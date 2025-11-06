package models;

import java.util.Comparator;

public class Student implements Comparable<Student>{
    private final String firstName;
    private final String lastName;
    private final int age;
    private final int grade; // from 1 to 11

    public Student(StudentBuilder builder) {
        if(builder == null) throw new IllegalArgumentException("builder cannot be null");
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.grade = builder.grade;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public int getGrade() { return grade; }

    @Override
    public String toString() {
        return "Student [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", grade=" + grade + "]";
    }

    @Override
    public int compareTo(Student otherStudent) {
        return Comparator.comparing(Student::getLastName)
                .thenComparing(Student::getFirstName)
                .thenComparingInt(Student::getGrade)
                .thenComparingInt(Student::getAge)
                .compare(this, otherStudent);
    }

    public static class StudentBuilder {
        private String firstName;
        private String lastName;
        private int age;
        private int grade;

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

        public Student build() {
            if(!validateStudent()) {
                throw new IllegalArgumentException("Some of the fields are invalid");
            }
            return new Student(this);
        }

        private boolean validateStudent() {
            return (firstName != null && !firstName.trim().isEmpty()
                    && lastName != null && !lastName.trim().isEmpty()
                    && age >= 0
                    && grade > 0 && grade <= 11);
        }
    }
}
