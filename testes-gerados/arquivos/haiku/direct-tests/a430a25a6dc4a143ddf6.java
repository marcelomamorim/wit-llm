/*
 * @test
 * @summary Verifies ConcurrentHashMap value spliterator tryAdvance behavior: null action, empty map, single element and full traversal
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Collection;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapReturnsFalseWithoutCallingAction();
        testSingleElementAdvancesOnce();
        testFullTraversalVisitsAllValues();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        Spliterator<Integer> s = map.values().spliterator();
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null action");
    }

    static void testEmptyMapReturnsFalseWithoutCallingAction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        Spliterator<Integer> s = map.values().spliterator();
        int[] calls = new int[1];
        Consumer<Integer> action = v -> calls[0]++;
        check(!s.tryAdvance(action), "tryAdvance on empty map should return false");
        check(calls[0] == 0, "action must not be invoked on empty map");
    }

    static void testSingleElementAdvancesOnce() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("k", 42);
        Spliterator<Integer> s = map.values().spliterator();
        int[] calls = new int[1];
        int[] seen = new int[1];
        Consumer<Integer> action = v -> {
            calls[0]++;
            seen[0] = v;
        };
        check(s.tryAdvance(action), "first tryAdvance should return true");
        check(calls[0] == 1, "action should be invoked once");
        check(seen[0] == 42, "action should receive the mapped value 42");
        check(!s.tryAdvance(action), "second tryAdvance should return false");
        check(calls[0] == 1, "action must not be invoked after exhaustion");
    }

    static void testFullTraversalVisitsAllValues() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(i, i);
        }
        Collection<Integer> values = map.values();
        Spliterator<Integer> s = values.spliterator();
        long[] count = new long[1];
        long[] sum = new long[1];
        Consumer<Integer> action = v -> {
            count[0]++;
            sum[0] += v;
        };
        while (s.tryAdvance(action)) {
            // keep advancing until exhausted
        }
        check(count[0] == 100, "expected 100 values, got " + count[0]);
        check(sum[0] == 4950L, "expected sum 4950, got " + sum[0]);
        check(!s.tryAdvance(action), "spliterator should be exhausted");
    }
}
