/*
 * @test
 * @summary Verifies ConcurrentSkipListMap.floorEntry returns the greatest entry with key less than or equal to the argument
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

    static void testEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        check(map.floorEntry(1) == null, "floorEntry on empty map must return null");
    }

    static void testExactAndBetweenKeys() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");

        Map.Entry<Integer, String> e = map.floorEntry(10);
        check(e != null && e.getKey() == 10 && "ten".equals(e.getValue()), "exact match floor failed");

        e = map.floorEntry(15);
        check(e != null && e.getKey() == 10 && "ten".equals(e.getValue()), "floor between keys failed");

        e = map.floorEntry(29);
        check(e != null && e.getKey() == 20, "floor 29 should be 20");

        e = map.floorEntry(30);
        check(e != null && e.getKey() == 30 && "thirty".equals(e.getValue()), "exact last key floor failed");

        e = map.floorEntry(35);
        check(e != null && e.getKey() == 30, "floor above max should be max key");
    }

    static void testBelowMinimumReturnsNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        check(map.floorEntry(5) == null, "floorEntry below minimum must return null");
        check(map.floorEntry(9) == null, "floorEntry just below minimum must return null");
    }

    static void testReverseComparator() {
        Comparator<Integer> reverse = Comparator.reverseOrder();
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>(reverse);
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");

        // In reverse order, the sequence is 30, 20, 10; floor(15) is the entry
        // that is <= 15 in that ordering and closest to it, namely 20.
        Map.Entry<Integer, String> e = map.floorEntry(15);
        check(e != null && e.getKey() == 20, "reverse comparator floor(15) should be 20");

        e = map.floorEntry(30);
        check(e != null && e.getKey() == 30, "reverse comparator floor(30) should be 30");
    }

    public static void main(String[] args) {
        testEmptyMap();
        testExactAndBetweenKeys();
        testBelowMinimumReturnsNull();
        testReverseComparator();
    }
}
