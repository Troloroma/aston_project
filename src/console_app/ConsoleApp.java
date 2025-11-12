package src.console_app;

import src.DataWriter;
import src.binary_search.BinarySearch;
import src.counter.OccurrenceCounter;
import src.entity_generator.EntityGenerator;
import src.file_reader.FileEntityReader;
import src.io.IoConsole;
import src.models.*;
import src.sorter.BubbleSortThreadPool;
import src.sorter.BubbleSortThreadPoolEvenField;
import src.sorter.ISortStrategy;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Консольное приложение с циклом, реализующее:
 * <ul>
 *     <li>Выбор длины массива</li>
 *     <li>Выбор источника данных</li>
 *     <li>Вывод списка</li>
 *     <li>Запуск сортировки (с использованием {@link EntityComparator})</li>
 *     <li>Запуск бинарного поиска (заглушка)</li>
 * </ul>
 * <p>
 * Выход возможен только через пункт меню.
 */
public class ConsoleApp {

    private final Scanner scanner = new Scanner(System.in);
    private final EntityGenerator generator = new EntityGenerator();
    private List<ComparableEntity> people = new ArrayList<>();
    private int arrayLength = 10;

    public void run() {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> setArrayLength();
                case "2" -> chooseDataSource();
                case "3" -> showPeople();
                case "4" -> sortPeople();
                case "5" -> binarySearchById();
                case "6" -> saveToFile();
                case "7" -> countElementOccurrences();
                case "8" -> sortPeopleEvenIds();
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
                1 - Задать длину массива (по умолчанию 10)
                2 - Выбрать источник данных (генерация / ввод вручную / файл)
                3 - Показать текущие данные
                4 - Сортировка списка
                5 - Бинарный поиск
                6 - Сохранить данные в файл
                7 - Посчитать число совпадений по Id
                8 - Сортировка только чётных Id (нечётные остаются на местах)
                0 - Выход
                =========================
                """);
    }

    private void setArrayLength() {
        System.out.print("Введите длину массива: ");
        try {
            int v = Integer.parseInt(scanner.nextLine().trim());
            if (v <= 0) System.out.println("Должно быть положительное число.");
            else {
                arrayLength = v;
                System.out.println("Длина установлена: " + arrayLength);
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите целое число!");
        }
    }

    private void chooseDataSource() {
        System.out.println("""
                Выберите источник данных:
                1 - Случайная генерация
                2 - Ввод вручную
                3 - Загрузка из файла
                """);
        String input = scanner.nextLine().trim();

        switch (input) {
            case "1" -> {
                IdRegistry.clear();
                people = generator.generateRandomPeople(arrayLength);
                System.out.println("Сгенерировано объектов: " + people.size());
                System.out.println("\nСгенерированные объекты:");
                people.forEach(System.out::println);
            }
            case "2" -> {
                IdRegistry.clear();
                people = IoConsole.readCollectionFromConsole(arrayLength);
                System.out.println("Введено объектов: " + people.size());
            }
            case "3" -> {
                IdRegistry.clear();
                System.out.print("Введите путь к файлу: (например, D://people.txt)\n");
                String path = scanner.nextLine().trim();
                people = FileEntityReader.readFromFile(path);
                System.out.println("Загружено объектов: " + people.size());
                people.forEach(System.out::println);
            }
            default -> System.out.println("Некорректный выбор.");
        }
    }

    private void showPeople() {
        if (people == null || people.isEmpty()) {
            System.out.println("Список пуст. Сначала заполните данные.");
            return;
        }
        System.out.println("\nТекущие данные:");
        people.forEach(System.out::println);
    }

    private void sortPeople() {
        if (people == null || people.isEmpty()) {
            System.out.println("Список пуст — сортировка невозможна.");
            return;
        }

        System.out.println("Сортировка (BubbleSortThreadPool) с использованием 2 потоков...");
        System.out.println("Сортировка происходит в следующем порядке: сначала учителя, потом ученики.");
        System.out.println("Учителя: id -> фамилия -> имя -> предмет преподавания -> опыт работы (в годах)");
        System.out.println("Ученики: id -> фамилия -> имя -> класс -> возраст");
        ISortStrategy<ComparableEntity> sorter = new BubbleSortThreadPool<>();
        try {
            sorter.sort(people);
        } catch (UnsupportedOperationException ex) {
            // на случай если список оказался неизменяемым
            System.out.println("Список не изменяемый, делаю копию.");
            List<ComparableEntity> copy = new ArrayList<>(people);
            sorter.sort(copy);
            people = copy;
        }
        System.out.println("\nСписок после сортировки:");
        people.forEach(System.out::println);
    }

    private void sortPeopleEvenIds() {
        if (people == null || people.isEmpty()) {
            System.out.println("Список пуст — сортировка невозможна.");
            return;
        }

        System.out.println("Сортировка только элементов с чётным id, остальные остаются на своих местах.");
        BubbleSortThreadPoolEvenField<ComparableEntity> sorter = new BubbleSortThreadPoolEvenField<>(ComparableEntity::getId);
        try {
            sorter.sort(people);
        } catch (UnsupportedOperationException ex) {
            System.out.println("Список не изменяемый, делаю копию.");
            List<ComparableEntity> copy = new ArrayList<>(people);
            sorter.sort(copy);
            people = copy;
        }
        System.out.println("\nСписок после сортировки чётных id:");
        people.forEach(System.out::println);
    }

    private void binarySearchById() {
        if (people == null || people.isEmpty()) {
            System.out.println("Список пуст — поиск невозможен.");
            return;
        }

        System.out.print("Введите id для поиска: ");
        String raw = scanner.nextLine().trim();

        int targetId;
        try {
            targetId = Integer.parseInt(raw);
        } catch (NumberFormatException ex) {
            System.out.println("Ошибка: id должен быть числом.");
            return;
        }

        List<ComparableEntity> sortedById = new ArrayList<>(people);
        sortedById.sort(Comparator.comparingInt(ComparableEntity::getId));

        int index = BinarySearch.binarySearchById(sortedById, targetId);
        if (index >= 0) {
            ComparableEntity found = sortedById.get(index);
            System.out.println("Найден объект: " + found);
        } else {
            System.out.println("Объект с id " + targetId + " не найден.");
        }
    }
    private void saveToFile() {
        if (people == null || people.isEmpty()) {
            System.out.println("Список пуст — нечего сохранять.");
            return;
        }

        System.out.print("Введите путь для сохранения (например, D://people.txt): ");
        String path = scanner.nextLine().trim();

        DataWriter.writeToFile(path, people);
    }
    private void countElementOccurrences() {
        if (people == null || people.isEmpty()) {
            System.out.println("Список пуст — нечего анализировать.");
            return;
        }

        System.out.print("Введите ID (число), чтобы посчитать количество таких ID в коллекции: ");
        String raw = scanner.nextLine().trim();

        try {
            int targetId = Integer.parseInt(raw);
            List<Integer> ids = people.stream()
                    .map(ComparableEntity::getId)
                    .collect(Collectors.toList());

            OccurrenceCounter.countOccurrences(ids, targetId);

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректное число.");
        }
    }
}