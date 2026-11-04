/*
 * @test
 * @summary Verify ConcurrentSkipListMap.lowerKey returns the greatest key strictly less than the argument, or null when none exists
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(Object expected, Object actual, String msg) {
        if (!Objects.equals(expected, actual)) {
            throw new RuntimeException(msg + ": expected " + expected + " but was " + actual);
        }
    }

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> empty = new ConcurrentSkipListMap<>();
        check(null, empty.lowerKey(1), "empty map lowerKey(1)");

        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(3, "c");
        m.put(5, "e");
        check(1, m.lowerKey(3), "lowerKey(3)");
        check(3, m.lowerKey(4), "lowerKey(4)");
        check(5, m.lowerKey(6), "lowerKey(6)");
        check(null, m.lowerKey(1), "lowerKey(1) smallest key");
        check(null, m.lowerKey(0), "lowerKey(0) below all keys");

        m.remove(3);
        check(1, m.lowerKey(5), "lowerKey(5) after removing 3");

        ConcurrentSkipListMap<Integer, String> r = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        r.put(1, "a");
        r.put(3, "c");
        r.put(5, "e");
        check(5, r.lowerKey(3), "reverse comparator lowerKey(3)");
        check(null, r.lowerKey(5), "reverse comparator lowerKey(5)");
    }
}
