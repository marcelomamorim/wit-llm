/*
 * @test
 * @summary Checks ConcurrentSkipListMap values spliterator tryAdvance behavior: null action, empty map, ascending and reverse order, and exhausted state.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapReturnsFalse();
        testAscendingOrder();
        testReverseComparatorOrder();
        testExhaustedStaysFalse();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static List<String> drain(Spliterator<String> s) {
        List<String> out = new ArrayList<>();
        Consumer<String> c = out::add;
        while (s.tryAdvance(c)) {
            // keep advancing until false
        }
        return out;
    }

    static void testNullActionThrowsNPE() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        Spliterator<String> s = m.values().spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) must throw NullPointerException");
    }

    static void testEmptyMapReturnsFalse() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        Spliterator<String> s = m.values().spliterator();
        final int[] calls = {0};
        boolean advanced = s.tryAdvance(v -> calls[0]++);
        check(!advanced, "tryAdvance on empty map must return false");
        check(calls[0] == 0, "action must not be invoked on empty map");
    }

    static void testAscendingOrder() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(3, "c");
        m.put(1, "a");
        m.put(2, "b");
        List<String> got = drain(m.values().spliterator());
        check(got.equals(java.util.Arrays.asList("a", "b", "c")),
              "expected ascending key order, got " + got);
    }

    static void testReverseComparatorOrder() {
        ConcurrentSkipListMap<Integer, String> m =
            new ConcurrentSkipListMap<>(Comparator.<Integer>reverseOrder());
        m.put(1, "a");
        m.put(3, "c");
        m.put(2, "b");
        List<String> got = drain(m.values().spliterator());
        check(got.equals(java.util.Arrays.asList("c", "b", "a")),
              "expected comparator order, got " + got);
    }

    static void testExhaustedStaysFalse() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "x");
        Spliterator<String> s = m.values().spliterator();
        final int[] calls = {0};
        check(s.tryAdvance(v -> calls[0]++), "first tryAdvance should return true");
        check(!s.tryAdvance(v -> calls[0]++), "second tryAdvance should return false");
        check(!s.tryAdvance(v -> calls[0]++), "tryAdvance after exhaustion should return false");
        check(calls[0] == 1, "action must be invoked exactly once, was " + calls[0]);
    }
}
