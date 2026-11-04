/*
 * @test
 * @summary Checks public keySet().spliterator().tryAdvance behavior of TreeMap: ordered traversal, empty map, null action and concurrent modification detection
 * @run main TreeMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Spliterator;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testOrderedAdvance();
        testEmptyMap();
        testNullAction();
        testConcurrentModification();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testOrderedAdvance() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(3, "c");
        m.put(1, "a");
        m.put(2, "b");
        Spliterator<Integer> sp = m.keySet().spliterator();
        List<Integer> seen = new ArrayList<>();
        check(sp.tryAdvance(seen::add), "first tryAdvance should return true");
        check(sp.tryAdvance(seen::add), "second tryAdvance should return true");
        check(sp.tryAdvance(seen::add), "third tryAdvance should return true");
        check(!sp.tryAdvance(seen::add), "tryAdvance past end should return false");
        check(seen.size() == 3, "expected 3 keys, got " + seen.size());
        check(seen.get(0) == 1 && seen.get(1) == 2 && seen.get(2) == 3,
              "keys not in ascending order: " + seen);
    }

    static void testEmptyMap() {
        TreeMap<Integer, String> m = new TreeMap<>();
        Spliterator<Integer> sp = m.keySet().spliterator();
        List<Integer> seen = new ArrayList<>();
        check(!sp.tryAdvance(seen::add), "tryAdvance on empty map should return false");
        check(seen.isEmpty(), "action must not be invoked on empty map");
    }

    static void testNullAction() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        Spliterator<Integer> sp = m.keySet().spliterator();
        try {
            sp.tryAdvance(null);
            throw new RuntimeException("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testConcurrentModification() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        Spliterator<Integer> sp = m.keySet().spliterator();
        try {
            sp.tryAdvance(k -> m.put(100, "x"));
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
