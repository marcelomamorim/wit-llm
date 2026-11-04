/*
 * @test
 * @summary Witup-derived tests for HashMap key spliterator forEachRemaining
 * @run main HashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Spliterator;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testVisitsAllKeys();
        testEmptyMapVisitsNothing();
        testConcurrentModificationDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        Spliterator<String> s = m.keySet().spliterator();
        boolean thrown = false;
        try {
            s.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testVisitsAllKeys() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        Set<String> seen = new HashSet<>();
        m.keySet().spliterator().forEachRemaining(k -> seen.add(k));
        Set<String> expected = new HashSet<>(new ArrayList<>(m.keySet()));
        check(seen.equals(expected), "forEachRemaining did not visit all keys: " + seen);
        check(seen.size() == 3, "unexpected number of visited keys: " + seen.size());
    }

    static void testEmptyMapVisitsNothing() {
        HashMap<String, String> m = new HashMap<>();
        int[] count = new int[1];
        m.keySet().spliterator().forEachRemaining(k -> count[0]++);
        check(count[0] == 0, "empty map should visit no keys, visited " + count[0]);
    }

    static void testConcurrentModificationDetected() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        Spliterator<String> s = m.keySet().spliterator();
        boolean thrown = false;
        try {
            s.forEachRemaining(k -> m.put("zz" + k, 0));
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural modification");
    }
}
