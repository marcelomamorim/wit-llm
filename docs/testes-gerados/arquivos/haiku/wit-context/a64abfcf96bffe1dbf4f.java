/*
 * @test
 * @summary WITUP generated checks for TreeMap descending key spliterator tryAdvance
 * @run main TreeMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Spliterator;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapReturnsFalse();
        testDescendingOrderAndExhaustion();
        testConcurrentModificationDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNPE() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        Spliterator<String> s = map.descendingKeySet().spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) must throw NullPointerException");
    }

    static void testEmptyMapReturnsFalse() {
        TreeMap<String, Integer> map = new TreeMap<>();
        Spliterator<String> s = map.descendingKeySet().spliterator();
        List<String> seen = new ArrayList<>();
        boolean advanced = s.tryAdvance(k -> seen.add(k));
        check(!advanced, "tryAdvance on empty map must return false");
        check(seen.isEmpty(), "action must not be invoked on empty map");
    }

    static void testDescendingOrderAndExhaustion() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        Spliterator<String> s = map.descendingKeySet().spliterator();
        List<String> seen = new ArrayList<>();
        check(s.tryAdvance(k -> seen.add(k)), "first tryAdvance must return true");
        check(s.tryAdvance(k -> seen.add(k)), "second tryAdvance must return true");
        check(s.tryAdvance(k -> seen.add(k)), "third tryAdvance must return true");
        check(!s.tryAdvance(k -> seen.add(k)), "fourth tryAdvance must return false");
        check(seen.size() == 3, "expected 3 keys, got " + seen);
        check(seen.get(0).equals("c") && seen.get(1).equals("b") && seen.get(2).equals("a"),
              "expected descending order [c, b, a], got " + seen);
    }

    static void testConcurrentModificationDetected() {
        final TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        Spliterator<String> s = map.descendingKeySet().spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(k -> map.put("z", 0));
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during action must cause ConcurrentModificationException");
    }
}
