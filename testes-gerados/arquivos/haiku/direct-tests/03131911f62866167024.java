/*
 * @test
 * @summary HashMap keySet spliterator tryAdvance: null action, empty map, full iteration and CME detection
 * @run main HashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testNullAction();
        testEmptyMap();
        testIteratesAllKeysOnce();
        testConcurrentModificationDuringAction();
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    static Map<String, Integer> sample() {
        Map<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        return m;
    }

    static void testNullAction() {
        Spliterator<String> s = sample().keySet().spliterator();
        try {
            s.tryAdvance(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testEmptyMap() {
        Spliterator<String> s = new HashMap<String, Integer>().keySet().spliterator();
        boolean advanced = s.tryAdvance(k -> { throw new RuntimeException("action must not be called"); });
        check(!advanced, "empty map must return false from tryAdvance");
    }

    static void testIteratesAllKeysOnce() {
        Map<String, Integer> m = sample();
        Spliterator<String> s = m.keySet().spliterator();
        Set<String> seen = new HashSet<>();
        int count = 0;
        while (s.tryAdvance(k -> seen.add(k))) {
            count++;
        }
        check(count == 3, "expected 3 elements, got " + count);
        check(seen.equals(m.keySet()), "visited keys must equal map keys");
        check(!s.tryAdvance(k -> seen.add(k)), "exhausted spliterator must return false");
    }

    static void testConcurrentModificationDuringAction() {
        Map<String, Integer> m = sample();
        Spliterator<String> s = m.keySet().spliterator();
        try {
            s.tryAdvance(k -> m.put("new", 0));
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
