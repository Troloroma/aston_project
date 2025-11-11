package src.models;

import java.util.HashSet;
import java.util.Set;


/**
 * Класс для хранения и проверки использованных id, чтобы избежать дубликат
 */
public final class IdRegistry {
    private static final Set<Integer> USED_IDS = new HashSet<>();

    private IdRegistry() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static synchronized boolean isIdUsed(int id) {
        return USED_IDS.contains(id);
    }

    public static synchronized void registerId(int id) {
        if (isIdUsed(id)) {
            throw new IllegalArgumentException("ID " + id + " уже используется!");
        }
        USED_IDS.add(id);
    }

    public static synchronized void clear() {
        USED_IDS.clear();
    }
}