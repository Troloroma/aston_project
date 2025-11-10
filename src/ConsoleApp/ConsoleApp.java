package src.ConsoleApp;

import src.models.*;
import src.PersonGenerator.PersonGenerator;

import java.util.*;

/**
 * Консольное приложение с циклом, реализующее:
 * <ul>
 *     <li>Выбор длины массива</li>
 *     <li>Выбор источника данных</li>
 *     <li>Вывод списка</li>
 *     <li>Запуск сортировки (с использованием {@link PersonComparator})</li>
 *     <li>Запуск бинарного поиска (заглушка)</li>
 * </ul>
 *
 * Выход возможен только через пункт меню.
 */
public class ConsoleApp {

    private final Scanner scanner = new Scanner(System.in);
    private final PersonGenerator generator = new PersonGenerator();
    private List<Person> people = new ArrayList<>();
    private int arrayLength = 10;

    /**
     * Запуск консольного цикла программы.
     */
    public void run() {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> setArrayLength();
                case "2" -> chooseDataSource();
                case "3" -> showPeople();
                case "4" -> sortPeople();
                case "5" -> binarySearchStub();
                case "0" -> {
                    System.out.println("Выход из программы...");
                    return;
                }
                default -> System.out.println("Некорректный ввод. Повторите выбор.");
            }
        }
    }

    private void printMenu() {
        System.out.println("""
                
                ========== МЕНЮ ==========
                1 - Задать длину массива
                2 - Выбрать источник данных
                3 - Показать текущие данные
                4 - Сортировка списка
                5 - Бинарный поиск
                0 - Выход
                ==========================
                """);
    }

    private void setArrayLength() {
        System.out.print("Введите длину массива: ");
        try {
            arrayLength = Integer.parseInt(scanner.nextLine().trim());
            System.out.println(" Длина установлена: " + arrayLength);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите целое число!");
        }
    }

    private void chooseDataSource() {
        System.out.println("""
                Выберите источник данных:
                1 - Случайная генерация
                2 - Ввод вручную
                3 - Загрузка из файла (заглушка)
                """);
        String input = scanner.nextLine().trim();

        switch (input) {
            case "1" -> {
                people = generator.generateRandomPeople(arrayLength);
                System.out.println(" Сгенерировано " + people.size() + " случайных объектов.");
            }
            case "2" -> manualInput();
            case "3" -> System.out.println(" Чтение из файла пока не реализовано.");
            default -> System.out.println("Некорректный выбор.");
        }
    }

    private void manualInput() {
        people.clear();
        for (int i = 0; i < arrayLength; i++) {
            System.out.print("\nВведите тип (student/teacher/exit): ");
            String type = scanner.nextLine().trim().toLowerCase();

            // Возможность выйти из ручного ввода
            if (type.equals("exit")) {
                System.out.println(" Ручной ввод завершён досрочно. Добавлено объектов: " + people.size());
                break;
            }

            try {
                if (type.equals("student")) {
                    System.out.print("Имя: ");
                    String fn = scanner.nextLine();
                    System.out.print("Фамилия: ");
                    String ln = scanner.nextLine();
                    System.out.print("Возраст: ");
                    int age = Integer.parseInt(scanner.nextLine());
                    System.out.print("Класс: ");
                    int grade = Integer.parseInt(scanner.nextLine());

                    Student s = new Student.StudentBuilder()
                            .firstName(fn)
                            .lastName(ln)
                            .age(age)
                            .grade(grade)
                            .id(i)
                            .build();
                    people.add(s);
                    System.out.println(" Студент добавлен.");
                } else if (type.equals("teacher")) {
                    System.out.print("Имя: ");
                    String fn = scanner.nextLine();
                    System.out.print("Фамилия: ");
                    String ln = scanner.nextLine();
                    System.out.print("Предмет (например, ALGEBRA, PHYSICS): ");
                    SubjectsEnum subject = SubjectsEnum.valueOf(scanner.nextLine().trim().toUpperCase());
                    System.out.print("Опыт (в годах): ");
                    int exp = Integer.parseInt(scanner.nextLine());

                    Teacher t = new Teacher.TeacherBuilder()
                            .firstName(fn)
                            .lastName(ln)
                            .subject(subject)
                            .experience(exp)
                            .id(i)
                            .build();
                    people.add(t);
                    System.out.println(" Учитель добавлен.");
                } else {
                    System.out.println(" Неизвестный тип, повторите ввод (или введите 'exit' для выхода).");
                    i--; // возвращаем итерацию, чтобы не терять позицию
                }
            } catch (Exception e) {
                System.out.println(" Ошибка ввода: " + e.getMessage());
                i--; // повторить ввод для текущего элемента
            }
        }

        System.out.println(" Итоговое количество введённых людей: " + people.size());
    }

    private void showPeople() {
        if (people.isEmpty()) {
            System.out.println(" Список пуст. Сначала заполните данные.");
            return;
        }
        System.out.println("\n Текущие данные:");
        people.forEach(System.out::println);
    }

    private void sortPeople() {
        if (people.isEmpty()) {
            System.out.println(" Список пуст — сортировка невозможна.");
            return;
        }
        System.out.println(" Сортировка по правилам PersonComparator...");
        people.sort(new PersonComparator());
        System.out.println(" Список отсортирован!");
    }

    private void binarySearchStub() {
        System.out.println(" Бинарный поиск пока не реализован (заглушка).");
    }

    public static void main(String[] args) {
        new ConsoleApp().run();
    }
}
