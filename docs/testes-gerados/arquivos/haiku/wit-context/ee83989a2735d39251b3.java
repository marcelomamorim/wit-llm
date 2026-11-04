/*
 * @test
 * @summary WITUP-derived checks for ConcurrentHashMap key spliterator forEachRemaining
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testForEachRemainingVisitsAllKeys();
        testEmptyMapDoesNotInvokeAction();
        testRemainingAfterTryAdvance();
    }

    static ConcurrentHashMap<String, Integer> sample() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        return m;
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullActionThrowsNpe() {
        Spliterator<String> s = sample().keySet().spliterator();
        boolean thrown = false;
        try {
            s.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");
    }

    static void testForEachRemainingVisitsAllKeys() {
        ConcurrentHashMap<String, Integer> m = sample();
        Spliterator<String> s = m.keySet().spliterator();
        List<String> seen = new ArrayList<>();
        s.forEachRemaining(seen::add);
        check(seen.size() == 3, "expected 3 keys, got " + seen.size());
        check(new HashSet<>(seen).equals(new HashSet<>(m.keySet())),
              "visited keys must equal map keys");
    }

    static void testEmptyMapDoesNotInvokeAction() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        Spliterator<String> s = m.keySet().spliterator();
        int[] calls = {0};
        s.forEachRemaining(k -> calls[0]++);
        check(calls[0] == 0, "action must not be invoked on empty map");
    }

    static void testRemainingAfterTryAdvance() {
        Spliterator<String> s = sample().keySet().spliterator();
        List<String> seen = new ArrayList<>();
        check(s.tryAdvance(seen::add), "tryAdvance should consume first key");
        s.forEachRemaining(seen::add);
        check(seen.size() == 3, "total visited keys must be 3, got " + seen.size());
        check(new HashSet<>(seen).size() == 3, "visited keys must be distinct");
    }
}
