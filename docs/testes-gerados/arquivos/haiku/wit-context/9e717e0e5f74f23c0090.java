/*
 * @test
 * @summary Checks HashMap values spliterator forEachRemaining null-action, traversal and concurrent modification behavior
 * @run main HashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Spliterator;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testVisitsAllValues();
        testConcurrentModificationDetected();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        Spliterator<Integer> s = m.values().spliterator();
        boolean thrown = false;
        try {
            s.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testVisitsAllValues() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        Spliterator<Integer> s = m.values().spliterator();
        final long[] sum = {0};
        final int[] count = {0};
        s.forEachRemaining(v -> {
            sum[0] += v;
            count[0]++;
        });
        check(count[0] == 3, "expected 3 values visited but was " + count[0]);
        check(sum[0] == 6, "expected sum 6 but was " + sum[0]);
    }

    static void testConcurrentModificationDetected() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        Spliterator<Integer> s = m.values().spliterator();
        final boolean[] done = {false};
        boolean thrown = false;
        try {
            s.forEachRemaining(v -> {
                if (!done[0]) {
                    done[0] = true;
                    m.put("new", 0);
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural modification");
    }
}
