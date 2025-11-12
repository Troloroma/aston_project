package src.io;

import src.models.IdRegistry;

import java.util.Arrays;
import java.util.Scanner;



public class ConsoleUtils {

    private ConsoleUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    static Scanner sc = new Scanner(System.in);

    public static String readLine(String prompt) {
        System.out.print(prompt);
        String line = sc.nextLine();
        while (line.trim().isEmpty()) {
            System.out.print("Поле не может быть пустым. Повторите ввод: ");
            line = sc.nextLine();
        }
        return line.trim();
    }

    public static int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int val = Integer.parseInt(sc.nextLine());
                if (val < min || val > max) {
                    System.out.println("Число должно быть в диапазоне [" + min + ", " + max + "]");
                } else return val;
            } catch (NumberFormatException e) {
                System.out.println("Некорректное число. Повторите ввод.");
            }
        }
    }

    public static int readUniqueId(String message) {
        while (true) {
            int id = readInt(message, 0, Integer.MAX_VALUE);
            if (IdRegistry.isIdUsed(id)) {
                System.out.println("Такой ID уже существует. Введите другой.");
            } else {
                return id;
            }
        }
    }

    public static <E extends Enum<E>> E readEnum(String prompt, Class<E> enumType) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim().toUpperCase();
            try {
                return Enum.valueOf(enumType, input);
            } catch (IllegalArgumentException e) {
                System.out.println("Такого значения нет. Возможные: " + Arrays.toString(enumType.getEnumConstants()));
            }
        }
    }
}
