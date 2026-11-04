/*
 * @test
 * @summary Verify that putIfAbsent on immutable maps created by Map.of throws UnsupportedOperationException and leaves the map unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {

    interface Action {
        void run();
    }

    static void expectUoe(String name, Action action) {
        try {
            action.run();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException(name + ": expected UnsupportedOperationException");
    }

    static void checkEmptyMap() {
        Map<String, Integer> m = Map.of();
        expectUoe("empty putIfAbsent new key", () -> m.putIfAbsent("k", 1));
        if (!m.isEmpty()) {
            throw new RuntimeException("empty map changed after putIfAbsent");
        }
    }

    static void checkSingleEntryMap() {
        Map<String, Integer> m = Map.of("a", 1);
        expectUoe("single putIfAbsent existing key", () -> m.putIfAbsent("a", 2));
        expectUoe("single putIfAbsent new key", () -> m.putIfAbsent("b", 3));
        if (m.size() != 1 || m.get("a") != 1 || m.containsKey("b")) {
            throw new RuntimeException("single-entry map changed after putIfAbsent");
        }
    }

    static void checkMultiEntryMap() {
        Map<String, Integer> m = Map.of("a", 1, "b", 2);
        expectUoe("multi putIfAbsent existing key", () -> m.putIfAbsent("a", 9));
        expectUoe("multi putIfAbsent new key", () -> m.putIfAbsent("c", 3));
        if (m.size() != 2 || m.get("a") != 1 || m.get("b") != 2 || m.containsKey("c")) {
            throw new RuntimeException("multi-entry map changed after putIfAbsent");
        }
    }

    public static void main(String[] args) {
        checkEmptyMap();
        checkSingleEntryMap();
        checkMultiEntryMap();
    }
}
