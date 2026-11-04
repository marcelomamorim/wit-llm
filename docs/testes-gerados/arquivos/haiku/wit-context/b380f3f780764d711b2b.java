/*
 * @test
 * @summary Witup-derived tests for TreeMap.replaceAll
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testNullFunctionThrowsNPE();
        testReplaceValues();
        testEmptyMapDoesNotCallFunction();
        testStructuralModificationThrowsCME();
    }

    static void testNullFunctionThrowsNPE() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        try {
            m.replaceAll(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testReplaceValues() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.replaceAll((k, v) -> v * 10 + k.length());
        if (m.get("a") != 11) {
            throw new RuntimeException("unexpected value for a: " + m.get("a"));
        }
        if (m.get("b") != 21) {
            throw new RuntimeException("unexpected value for b: " + m.get("b"));
        }
        if (m.size() != 2) {
            throw new RuntimeException("unexpected size: " + m.size());
        }
    }

    static void testEmptyMapDoesNotCallFunction() {
        TreeMap<String, Integer> m = new TreeMap<>();
        int[] calls = new int[1];
        m.replaceAll((k, v) -> {
            calls[0]++;
            return v;
        });
        if (calls[0] != 0) {
            throw new RuntimeException("function called on empty map: " + calls[0]);
        }
        if (!m.isEmpty()) {
            throw new RuntimeException("map should remain empty");
        }
    }

    static void testStructuralModificationThrowsCME() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        m.put("b", 2);
        try {
            m.replaceAll((k, v) -> {
                if (k.equals("a")) {
                    m.put("z", 0);
                }
                return v;
            });
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (java.util.ConcurrentModificationException expected) {
        }
    }
}
