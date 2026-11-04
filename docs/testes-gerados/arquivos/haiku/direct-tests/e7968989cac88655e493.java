/*
 * @test
 * @summary Verifies ConcurrentSkipListMap.ceilingEntry returns the least entry with key >= given key, honoring the comparator
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> empty = new ConcurrentSkipListMap<>();
        check(empty.ceilingEntry(5) == null, "ceilingEntry on empty map must return null");

        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(10, "a");
        m.put(20, "b");
        m.put(30, "c");

        Map.Entry<Integer, String> e = m.ceilingEntry(10);
        check(e != null && e.getKey() == 10 && "a".equals(e.getValue()), "exact match expected for key 10");

        e = m.ceilingEntry(11);
        check(e != null && e.getKey() == 20 && "b".equals(e.getValue()), "next greater key expected for 11");

        e = m.ceilingEntry(0);
        check(e != null && e.getKey() == 10 && "a".equals(e.getValue()), "smallest key expected for 0");

        e = m.ceilingEntry(30);
        check(e != null && e.getKey() == 30 && "c".equals(e.getValue()), "exact match expected for key 30");

        check(m.ceilingEntry(31) == null, "no entry expected when key exceeds all keys");

        ConcurrentSkipListMap<Integer, String> rev = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        rev.put(10, "a");
        rev.put(20, "b");
        rev.put(30, "c");
        e = rev.ceilingEntry(15);
        check(e != null && e.getKey() == 10, "reverse comparator: ceiling of 15 must be key 10");

        System.out.println("PASSED");
    }
}
