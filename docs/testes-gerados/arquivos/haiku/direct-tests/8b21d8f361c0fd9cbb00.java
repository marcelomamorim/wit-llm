/*
 * @test
 * @summary HashMap.forEach(Consumer) visits all values, rejects null action, and detects structural modification
 * @run main HashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 10);
        try {
            map.forEach(null);
            throw new RuntimeException("expected NullPointerException for non-empty map");
        } catch (NullPointerException expected) {
        }

        Map<Integer, Integer> empty = new HashMap<>();
        try {
            empty.forEach(null);
            throw new RuntimeException("expected NullPointerException for empty map");
        } catch (NullPointerException expected) {
        }
    }

    static void testEmptyMapDoesNotInvokeAction() {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> seen = new ArrayList<>();
        map.forEach(v -> seen.add(v));
        check(seen.isEmpty(), "action must not be called on empty map");
    }

    static void testVisitsAllValues() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            map.put(i, i * 3);
        }
        List<Integer> seen = new ArrayList<>();
        map.forEach(v -> seen.add(v));
        check(seen.size() == 20, "expected 20 visited values, got " + seen.size());
        int sum = 0;
        for (int v : seen) {
            sum += v;
        }
        int expectedSum = 0;
        for (int i = 0; i < 20; i++) {
            expectedSum += i * 3;
        }
        check(sum == expectedSum, "sum of visited values mismatch");
    }

    static void testVisitsNullValues() {
        Map<String, String> map = new HashMap<>();
        map.put("a", null);
        map.put("b", "x");
        List<String> seen = new ArrayList<>();
        map.forEach(v -> seen.add(v));
        check(seen.size() == 2, "expected 2 visited entries");
        check(seen.contains(null), "null value must be passed to action");
        check(seen.contains("x"), "non-null value must be passed to action");
    }

    static void testStructuralModificationThrowsCME() {
        Map<Integer, Integer> map = new HashMap<>(64);
        map.put(1, 1);
        boolean thrown = false;
        try {
            map.forEach(v -> map.put(100, 1));
        } catch (ConcurrentModificationException expected) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural modification");
    }

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapDoesNotInvokeAction();
        testVisitsAllValues();
        testVisitsNullValues();
        testStructuralModificationThrowsCME();
    }
}
