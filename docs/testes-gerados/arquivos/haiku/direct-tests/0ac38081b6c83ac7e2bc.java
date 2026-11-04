/*
 * @test
 * @summary Verifies ConcurrentHashMap value iterator forEachRemaining behavior: full traversal, traversal after partial iteration, empty map, and null action handling
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingVisitsAllValues();
        testForEachRemainingAfterPartialIteration();
        testForEachRemainingOnEmptyMap();
        testForEachRemainingNullActionThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ConcurrentHashMap<Integer, Integer> newMap() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(1, 10);
        map.put(2, 20);
        map.put(3, 30);
        return map;
    }

    static void testForEachRemainingVisitsAllValues() {
        ConcurrentHashMap<Integer, Integer> map = newMap();
        Iterator<Integer> it = map.values().iterator();
        final int[] sum = {0};
        final int[] count = {0};
        it.forEachRemaining(v -> {
            sum[0] += v;
            count[0]++;
        });
        check(count[0] == 3, "expected 3 values visited, got " + count[0]);
        check(sum[0] == 60, "expected sum 60, got " + sum[0]);
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testForEachRemainingAfterPartialIteration() {
        ConcurrentHashMap<Integer, Integer> map = newMap();
        Iterator<Integer> it = map.values().iterator();
        int first = it.next();
        final int[] sum = {0};
        final int[] count = {0};
        it.forEachRemaining(v -> {
            sum[0] += v;
            count[0]++;
        });
        check(count[0] == 2, "expected 2 remaining values, got " + count[0]);
        check(sum[0] == 60 - first, "remaining sum mismatch: " + sum[0]);
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testForEachRemainingOnEmptyMap() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        Iterator<Integer> it = map.values().iterator();
        final int[] count = {0};
        it.forEachRemaining(v -> count[0]++);
        check(count[0] == 0, "action should not be invoked on empty map");
    }

    static void testForEachRemainingNullActionThrowsNPE() {
        ConcurrentHashMap<Integer, Integer> map = newMap();
        Iterator<Integer> it = map.values().iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }
}
