/*
 * @test
 * @summary Witup-generated tests for ConcurrentSkipListMap.KeySpliterator.forEachRemaining via keySet().spliterator()
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testForEachRemainingVisitsKeysInOrder() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(3, "c");
        map.put(1, "a");
        map.put(5, "e");
        map.put(2, "b");
        map.put(4, "d");

        Spliterator<Integer> sp = map.keySet().spliterator();
        List<Integer> seen = new ArrayList<>();
        sp.forEachRemaining(k -> seen.add(k));

        check(seen.equals(List.of(1, 2, 3, 4, 5)),
              "expected ascending keys [1..5] but got " + seen);
    }

    static void testForEachRemainingOnEmptyMapDoesNothing() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        Spliterator<Integer> sp = map.keySet().spliterator();
        List<Integer> seen = new ArrayList<>();
        sp.forEachRemaining(k -> seen.add(k));

        check(seen.isEmpty(), "expected no keys for empty map but got " + seen);
    }

    static void testForEachRemainingSecondCallYieldsNothing() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "x");
        map.put(20, "y");

        Spliterator<Integer> sp = map.keySet().spliterator();
        List<Integer> first = new ArrayList<>();
        sp.forEachRemaining(k -> first.add(k));
        List<Integer> second = new ArrayList<>();
        sp.forEachRemaining(k -> second.add(k));

        check(first.equals(List.of(10, 20)),
              "expected [10, 20] on first traversal but got " + first);
        check(second.isEmpty(),
              "expected no keys on exhausted spliterator but got " + second);
    }

    static void testForEachRemainingNullActionThrowsNPE() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        Spliterator<Integer> sp = map.keySet().spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    public static void main(String[] args) {
        testForEachRemainingVisitsKeysInOrder();
        testForEachRemainingOnEmptyMapDoesNothing();
        testForEachRemainingSecondCallYieldsNothing();
        testForEachRemainingNullActionThrowsNPE();
    }
}
