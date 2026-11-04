/*
 * @test
 * @summary Verifies that replaceAll on immutable maps created by Map.of and Map.copyOf throws UnsupportedOperationException and leaves contents unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.HashMap;
import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        checkEmpty();
        checkSingle();
        checkMultiple();
        checkCopyOf();
    }

    static void checkEmpty() {
        Map<String, Integer> m = Map.of();
        expectUoe(m, "empty");
        if (!m.isEmpty()) {
            throw new RuntimeException("empty map modified");
        }
    }

    static void checkSingle() {
        Map<String, Integer> m = Map.of("a", 1);
        expectUoe(m, "single");
        if (m.get("a") != 1 || m.size() != 1) {
            throw new RuntimeException("single map modified");
        }
    }

    static void checkMultiple() {
        Map<String, Integer> m = Map.of("a", 1, "b", 2, "c", 3);
        expectUoe(m, "multiple");
        if (m.get("a") != 1 || m.get("b") != 2 || m.get("c") != 3 || m.size() != 3) {
            throw new RuntimeException("multiple map modified");
        }
    }

    static void checkCopyOf() {
        HashMap<String, Integer> src = new HashMap<>();
        src.put("x", 10);
        src.put("y", 20);
        Map<String, Integer> m = Map.copyOf(src);
        expectUoe(m, "copyOf");
        if (m.get("x") != 10 || m.get("y") != 20 || m.size() != 2) {
            throw new RuntimeException("copyOf map modified");
        }
    }

    static void expectUoe(Map<String, Integer> m, String name) {
        try {
            m.replaceAll((k, v) -> v + 1);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException(name + ": expected UnsupportedOperationException");
    }
}
