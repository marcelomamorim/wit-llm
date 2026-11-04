/*
 * @test
 * @summary Witup-derived checks for ConcurrentSkipListMap key spliterator forEachRemaining
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testVisitsKeysInAscendingOrder();
        testEmptyMapInvokesNothing();
        testExhaustedSpliteratorInvokesNothing();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNpe() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        Spliterator<Integer> sp = m.keySet().spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testVisitsKeysInAscendingOrder() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(3, "c");
        m.put(1, "a");
        m.put(2, "b");
        Spliterator<Integer> sp = m.keySet().spliterator();
        List<Integer> out = new ArrayList<>();
        sp.forEachRemaining(k -> out.add(k));
        check(out.size() == 3, "expected 3 keys, got " + out.size());
        check(out.get(0) == 1 && out.get(1) == 2 && out.get(2) == 3,
              "expected ascending keys [1, 2, 3], got " + out);
    }

    static void testEmptyMapInvokesNothing() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        Spliterator<Integer> sp = m.keySet().spliterator();
        int[] count = new int[1];
        sp.forEachRemaining(k -> count[0]++);
        check(count[0] == 0, "expected no action calls on empty map, got " + count[0]);
    }

    static void testExhaustedSpliteratorInvokesNothing() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(2, "b");
        Spliterator<Integer> sp = m.keySet().spliterator();
        int[] first = new int[1];
        sp.forEachRemaining(k -> first[0]++);
        check(first[0] == 2, "expected 2 keys on first traversal, got " + first[0]);
        int[] second = new int[1];
        sp.forEachRemaining(k -> second[0]++);
        check(second[0] == 0, "expected no keys on second traversal, got " + second[0]);
    }
}
