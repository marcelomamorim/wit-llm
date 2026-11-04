/*
 * @test
 * @summary ConcurrentSkipListMap value spliterator forEachRemaining: ordering, consumption, null action
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(3, "c");
        map.put(1, "a");
        map.put(2, "b");

        // Values are visited in ascending key order.
        List<String> out = new ArrayList<>();
        map.values().spliterator().forEachRemaining(out::add);
        List<String> expected = new ArrayList<>();
        expected.add("a");
        expected.add("b");
        expected.add("c");
        check(out.equals(expected), "ascending order mismatch: " + out);

        // A second call on the same spliterator has nothing left to visit.
        List<String> second = new ArrayList<>();
        java.util.Spliterator<String> sp = map.values().spliterator();
        sp.forEachRemaining(v -> { });
        sp.forEachRemaining(second::add);
        check(second.isEmpty(), "expected no values after consumption: " + second);

        // Empty map: action is never invoked.
        ConcurrentSkipListMap<Integer, String> empty = new ConcurrentSkipListMap<>();
        List<String> none = new ArrayList<>();
        empty.values().spliterator().forEachRemaining(none::add);
        check(none.isEmpty(), "empty map should produce no values");

        // Reverse comparator changes the visiting order.
        ConcurrentSkipListMap<Integer, String> rev = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        rev.put(1, "a");
        rev.put(2, "b");
        rev.put(3, "c");
        List<String> revOut = new ArrayList<>();
        rev.values().spliterator().forEachRemaining(revOut::add);
        List<String> revExpected = new ArrayList<>();
        revExpected.add("c");
        revExpected.add("b");
        revExpected.add("a");
        check(revOut.equals(revExpected), "reverse order mismatch: " + revOut);

        // Null action must throw NullPointerException, without consuming elements.
        boolean thrown = false;
        try {
            map.values().spliterator().forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action should throw NullPointerException");

        List<String> afterNull = new ArrayList<>();
        map.values().spliterator().forEachRemaining(afterNull::add);
        check(afterNull.equals(expected), "elements should remain after null action: " + afterNull);
    }
}
