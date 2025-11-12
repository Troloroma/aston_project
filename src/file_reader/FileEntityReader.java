package src.file_reader;

import src.models.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * Читает список ComparableEntity (Teacher / Student) из текстового файла.
 * Формат файла:
 * <ul>
 *     <li>Учитель
 *     <li>Имя: Илья
 * <li>Фамилия: Иванов
 * <li>id: 0
 * <li>Предмет: История
 * <li>Стаж: 10
 * <li></li>
 * <li>Ученик
 * <li>Имя: Мария
 * <li>Фамилия: Сидорова
 * <li>id: 1
 * <li>Класс: 8
 * <li>Возраст: 14</li>
 *     </ul>
 */
public class FileEntityReader {

    private FileEntityReader() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static List<ComparableEntity> readFromFile(String filePath) {
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            List<String> content = lines
                    .filter(line -> !line.trim().isEmpty())
                    .toList();

            List<ComparableEntity> entities = new ArrayList<>();
            Map<String, String> current = new LinkedHashMap<>();
            String type = null;

            for (String line : content) {
                line = line.trim();

                // Если встретили заголовок нового объекта
                if (line.equalsIgnoreCase("Учитель") || line.equalsIgnoreCase("Ученик")) {
                    // Если предыдущий объект наполнен — пробуем создать его
                    addEntityIfValid(type, current, entities);

                    type = line;
                    current.clear();
                } else if (line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) {
                        current.put(parts[0].trim(), parts[1].trim());
                    }
                }
            }

            // Добавляем последний объект
            addEntityIfValid(type, current, entities);

            return entities;

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return List.of();
        }
    }

    private static void addEntityIfValid(String type, Map<String, String> data, List<ComparableEntity> entities) {
        if (type == null || data.isEmpty()) return;

        try {
            ComparableEntity entity = switch (type.toLowerCase()) {
                case "учитель" -> buildTeacher(data);
                case "ученик" -> buildStudent(data);
                default -> null;
            };

            // Добавляем только если объект успешно создан
            if (entity != null) {
                entities.add(entity);
            }

        } catch (Exception e) {
            System.out.println("Пропуск некорректного объекта (" + type + "): " + e.getMessage());
        }
    }

    private static Teacher buildTeacher(Map<String, String> data) {
        try {
            return new Teacher.TeacherBuilder()
                    .firstName(data.getOrDefault("Имя", ""))
                    .lastName(data.getOrDefault("Фамилия", ""))
                    .experience(Integer.parseInt(data.getOrDefault("Стаж", "0")))
                    .subject(parseSubject(data.get("Предмет")))
                    .id(Integer.parseInt(data.getOrDefault("id", "0")))
                    .build();
        } catch (Exception e) {
            System.out.println("Ошибка при создании учителя: " + e.getMessage());
            return null;
        }
    }

    private static Student buildStudent(Map<String, String> data) {
        try {
            int grade = Integer.parseInt(data.getOrDefault("Класс", "0"));
            int age = Integer.parseInt(data.getOrDefault("Возраст", "0"));
            if (grade < 1 || grade > 11) {
                System.out.println("Пропуск ученика: некорректный класс (" + grade + ")");
                return null;
            }

            return new Student.StudentBuilder()
                    .firstName(data.getOrDefault("Имя", ""))
                    .lastName(data.getOrDefault("Фамилия", ""))
                    .grade(grade)
                    .age(age)
                    .id(Integer.parseInt(data.getOrDefault("id", "0")))
                    .build();
        } catch (Exception e) {
            System.out.println("Ошибка при создании ученика: " + e.getMessage());
            return null;
        }
    }

    private static SubjectsEnum parseSubject(String subjectName) {
        if (subjectName == null) return SubjectsEnum.OTHER;
        return Arrays.stream(SubjectsEnum.values())
                .filter(s -> s.toString().equalsIgnoreCase(subjectName.trim()))
                .findFirst()
                .orElse(SubjectsEnum.OTHER);
    }
}
