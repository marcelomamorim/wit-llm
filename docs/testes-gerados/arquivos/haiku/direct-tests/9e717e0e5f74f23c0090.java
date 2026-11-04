/*
 * @test
 * @summary Validates HashMap values spliterator forEachRemaining: null action, full traversal, traversal after tryAdvance, empty map, and CME detection
 * @run main HashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Spliterator;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testVisitsAllValues();
        testVisitsRemainingAfterTryAdvance();
        testEmptyMapNoAction();
        testConcurrentModificationDetected();
    }

    static void testNullActionThrowsNpe() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        Spliterator<Integer> s = m.values().spliterator();
        boolean thrown = false;
        try {
            s.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null action");
        }
    }

    static void testVisitsAllValues() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        List<Integer> seen = new ArrayList<>();
        m.values().spliterator().forEachRemaining(v -> seen.add(v));
        if (seen.size() != 3) {
            throw new RuntimeException("expected 3 values, got " + seen.size());
        }
        int sum = 0;
        for (Integer v : seen) {
            sum += v;
        }
        if (sum != 6) {
            throw new RuntimeException("expected sum 6, got " + sum);
        }
    }

    static void testVisitsRemainingAfterTryAdvance() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        Spliterator<Integer> s = m.values().spliterator();
        List<Integer> first = new ArrayList<>();
        if (!s.tryAdvance(v -> first.add(v))) {
            throw new RuntimeException("tryAdvance should consume one element");
        }
        List<Integer> rest = new ArrayList<>();
        s.forEachRemaining(v -> rest.add(v));
        if (first.size() + rest.size() != 3) {
            throw new RuntimeException("expected total 3 values, got "
                    + (first.size() + rest.size()));
        }
        int sum = 0;
        for (Integer v : first) {
            sum += v;
        }
        for (Integer v : rest) {
            sum += v;
        }
        if (sum != 6) {
            throw new RuntimeException("expected sum 6, got " + sum);
        }
    }

    static void testEmptyMapNoAction() {
        HashMap<String, Integer> m = new HashMap<>();
        int[] count = new int[1];
        m.values().spliterator().forEachRemaining(v -> count[0]++);
        if (count[0] != 0) {
            throw new RuntimeException("expected no action calls, got " + count[0]);
        }
    }

    static void testConcurrentModificationDetected() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        boolean[] added = new boolean[1];
        boolean thrown = false;
        try {
            m.values().spliterator().forEachRemaining(v -> {
                if (!added[0]) {
                    added[0] = true;
                    m.put("b", 2);
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected ConcurrentModificationException");
        }
    }
}
