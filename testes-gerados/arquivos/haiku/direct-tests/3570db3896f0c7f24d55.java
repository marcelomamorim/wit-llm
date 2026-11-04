/*
 * @test
 * @summary Tests HashMap key spliterator forEachRemaining: null action, empty map, full traversal, repeated calls and concurrent modification detection
 * @run main HashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

public class HashMapWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapDoesNotInvokeAction();
        testVisitsAllKeys();
        testSecondCallVisitsNothing();
        testConcurrentModificationDetected();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    static void testNullActionThrowsNPE() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        Spliterator<Integer> sp = map.keySet().spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action must throw NullPointerException");
    }

    static void testEmptyMapDoesNotInvokeAction() {
        Map<Integer, Integer> map = new HashMap<>();
        Spliterator<Integer> sp = map.keySet().spliterator();
        final int[] calls = {0};
        Consumer<Integer> action = k -> calls[0]++;
        sp.forEachRemaining(action);
        check(calls[0] == 0, "empty map must not invoke action, calls=" + calls[0]);
    }

    static void testVisitsAllKeys() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            map.put(i, i * 10);
        }
        Spliterator<Integer> sp = map.keySet().spliterator();
        List<Integer> seen = new ArrayList<>();
        Consumer<Integer> action = k -> seen.add(k);
        sp.forEachRemaining(action);
        check(seen.size() == 10, "expected 10 keys visited, got " + seen.size());
        Set<Integer> unique = new java.util.HashSet<>(seen);
        check(unique.equals(map.keySet()), "visited keys must equal map keys");
    }

    static void testSecondCallVisitsNothing() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 2);
        Spliterator<Integer> sp = map.keySet().spliterator();
        final int[] calls = {0};
        Consumer<Integer> action = k -> calls[0]++;
        sp.forEachRemaining(action);
        check(calls[0] == 2, "first call must visit 2 keys, got " + calls[0]);
        sp.forEachRemaining(action);
        check(calls[0] == 2, "second call must visit nothing, total=" + calls[0]);
    }

    static void testConcurrentModificationDetected() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        Spliterator<Integer> sp = map.keySet().spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(k -> map.put(2, 2));
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during forEachRemaining must throw ConcurrentModificationException");
    }
}
