/*
 * @test
 * @summary Witup-style tests for ConcurrentHashMap.forEach
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testNullActionThrowsNpeOnNonEmptyMap();
        testEmptyMapNeverInvokesAction();
        testVisitsAllEntriesExactlyOnce();
        testSingleEntryIsVisited();
        testActionExceptionPropagates();
    }

    static void testNullActionThrowsNpe() {
        ConcurrentHashMap<Integer, Integer> m = new ConcurrentHashMap<>();
        boolean thrown = false;
        try {
            m.forEach((BiConsumer<Integer, Integer>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) on empty map must throw NullPointerException");
    }

    static void testNullActionThrowsNpeOnNonEmptyMap() {
        ConcurrentHashMap<Integer, Integer> m = new ConcurrentHashMap<>();
        m.put(1, 10);
        boolean thrown = false;
        try {
            m.forEach((BiConsumer<Integer, Integer>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) on non-empty map must throw NullPointerException");
    }

    static void testEmptyMapNeverInvokesAction() {
        ConcurrentHashMap<Integer, Integer> m = new ConcurrentHashMap<>();
        int[] calls = new int[1];
        m.forEach((k, v) -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked for an empty map");
    }

    static void testVisitsAllEntriesExactlyOnce() {
        ConcurrentHashMap<Integer, Integer> m = new ConcurrentHashMap<>();
        for (int i = 0; i < 100; i++) {
            m.put(i, i * 2);
        }
        Map<Integer, Integer> seen = new HashMap<>();
        int[] calls = new int[1];
        m.forEach((k, v) -> {
            calls[0]++;
            Integer prev = seen.put(k, v);
            check(prev == null, "key visited more than once");
        });
        check(calls[0] == 100, "action must be invoked once per entry, got " + calls[0]);
        check(seen.equals(m), "visited entries must match map contents");
    }

    static void testSingleEntryIsVisited() {
        ConcurrentHashMap<Integer, Integer> m = new ConcurrentHashMap<>();
        m.put(42, 7);
        int[] sum = new int[2];
        m.forEach((k, v) -> {
            sum[0] += k;
            sum[1] += v;
        });
        check(sum[0] == 42, "visited key mismatch");
        check(sum[1] == 7, "visited value mismatch");
    }

    static void testActionExceptionPropagates() {
        ConcurrentHashMap<Integer, Integer> m = new ConcurrentHashMap<>();
        m.put(1, 1);
        boolean thrown = false;
        try {
            m.forEach((k, v) -> {
                throw new IllegalStateException("boom");
            });
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "exception thrown by action must propagate out of forEach");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
