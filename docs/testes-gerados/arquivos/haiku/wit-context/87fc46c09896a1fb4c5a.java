/*
 * @test
 * @summary Verify null-key rejection and lookup behavior of the MapN containsKey path via public Map.of factories
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testContainsKeyNullOnEmptyMap();
        testContainsKeyNullOnPopulatedMap();
        testContainsKeyPresentAndAbsent();
        testContainsKeyAfterRepeatedLookups();
    }

    static void testContainsKeyNullOnEmptyMap() {
        Map<String, Integer> m = Map.of();
        expectNpe(m);
    }

    static void testContainsKeyNullOnPopulatedMap() {
        Map<String, Integer> m = Map.of("a", 1, "b", 2, "c", 3);
        expectNpe(m);
    }

    static void testContainsKeyPresentAndAbsent() {
        Map<String, Integer> m = Map.of("a", 1, "b", 2, "c", 3);
        check(m.containsKey("a"), "containsKey(a) should be true");
        check(m.containsKey("b"), "containsKey(b) should be true");
        check(m.containsKey("c"), "containsKey(c) should be true");
        check(!m.containsKey("d"), "containsKey(d) should be false");
        check(!m.containsKey(""), "containsKey(empty string) should be false");
    }

    static void testContainsKeyAfterRepeatedLookups() {
        Map<String, Integer> m = Map.of("x", 10, "y", 20, "z", 30);
        for (int i = 0; i < 100; i++) {
            check(m.containsKey("x"), "repeated lookup x iteration " + i);
            check(!m.containsKey("missing"), "repeated lookup missing iteration " + i);
        }
    }

    static void expectNpe(Map<String, Integer> m) {
        boolean thrown = false;
        try {
            m.containsKey(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "containsKey(null) must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
