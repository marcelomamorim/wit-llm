/*
 * @test
 * @summary Behavioral tests for HashMap.forEach: null action, empty map, full traversal, value updates and concurrent modification detection
 * @run main HashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapNeverInvokesAction();
        testVisitsAllEntries();
        testUpdatingExistingKeyDoesNotThrow();
        testStructuralModificationThrowsCME();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        HashMap<String, Integer> nonEmpty = new HashMap<>();
        nonEmpty.put("a", 1);
        try {
            nonEmpty.forEach(null);
            throw new RuntimeException("expected NullPointerException for non-empty map");
        } catch (NullPointerException expected) {
        }

        HashMap<String, Integer> empty = new HashMap<>();
        try {
            empty.forEach(null);
            throw new RuntimeException("expected NullPointerException for empty map");
        } catch (NullPointerException expected) {
        }
    }

    static void testEmptyMapNeverInvokesAction() {
        HashMap<String, Integer> m = new HashMap<>();
        int[] calls = new int[1];
        m.forEach(e -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked on empty map");
    }

    static void testVisitsAllEntries() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        m.put("d", 4);
        m.put("e", 5);

        List<String> keys = new ArrayList<>();
        int[] sum = new int[1];
        m.forEach(e -> {
            keys.add(e.getKey());
            sum[0] += e.getValue();
        });

        check(keys.size() == 5, "expected 5 visited entries but got " + keys.size());
        check(sum[0] == 15, "expected value sum 15 but got " + sum[0]);
        check(keys.contains("a") && keys.contains("e"), "missing expected keys");
    }

    static void testUpdatingExistingKeyDoesNotThrow() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.forEach(e -> m.put(e.getKey(), e.getValue() + 10));
        check(m.get("a") == 11, "expected a=11 but got " + m.get("a"));
        check(m.get("b") == 12, "expected b=12 but got " + m.get("b"));
        check(m.size() == 2, "size must remain 2");
    }

    static void testStructuralModificationThrowsCME() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        boolean[] added = new boolean[1];
        try {
            m.forEach(e -> {
                if (!added[0]) {
                    added[0] = true;
                    m.put("zz", 99);
                }
            });
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
        check(m.containsKey("zz"), "structural insertion should have taken effect");
    }
}
