/*
 * @test
 * @summary Tests public entrySet().removeIf behavior of ConcurrentHashMap, including null filter rejection
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNPE();
        testRemovesMatchingEntries();
        testNoMatchReturnsFalse();
    }

    static void testNullFilterThrowsNPE() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("a", 1);
        boolean thrown = false;
        try {
            m.entrySet().removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null filter");
        }
        if (m.size() != 1 || m.get("a") != 1) {
            throw new RuntimeException("map modified after null filter rejection");
        }
    }

    static void testRemovesMatchingEntries() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 4);
        boolean changed = m.entrySet().removeIf(e -> e.getValue() % 2 == 0);
        if (!changed) {
            throw new RuntimeException("removeIf should report change");
        }
        if (m.size() != 1 || !m.containsKey("a") || m.containsKey("b") || m.containsKey("c")) {
            throw new RuntimeException("unexpected contents after removeIf: " + m);
        }
    }

    static void testNoMatchReturnsFalse() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        boolean changed = m.entrySet().removeIf(e -> e.getValue() > 100);
        if (changed) {
            throw new RuntimeException("removeIf should return false when nothing matches");
        }
        if (m.size() != 2) {
            throw new RuntimeException("map size changed unexpectedly");
        }
    }
}
