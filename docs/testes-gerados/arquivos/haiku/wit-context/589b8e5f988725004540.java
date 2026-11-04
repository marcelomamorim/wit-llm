/*
 * @test
 * @summary Witup-style tests for ConcurrentSkipListMap.higherKey
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullKeyThrowsNPE();
        testEmptyMapReturnsNull();
        testNaturalOrdering();
        testReverseOrdering();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullKeyThrowsNPE() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        try {
            map.higherKey(null);
            check(false, "higherKey(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }

        ConcurrentSkipListMap<Integer, String> empty = new ConcurrentSkipListMap<>();
        try {
            empty.higherKey(null);
            check(false, "higherKey(null) on empty map should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyMapReturnsNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        check(map.higherKey(0) == null, "higherKey on empty map should return null");
        check(map.higherKey(42) == null, "higherKey on empty map should return null");
    }

    static void testNaturalOrdering() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");
        map.put(5, "e");
        check(map.higherKey(0) == 1, "higherKey(0) should be 1");
        check(map.higherKey(2) == 3, "higherKey(2) should be 3");
        check(map.higherKey(3) == 5, "higherKey(3) should be 5");
        check(map.higherKey(5) == null, "higherKey(5) should be null");
        check(map.higherKey(6) == null, "higherKey(6) should be null");
    }

    static void testReverseOrdering() {
        Comparator<Integer> rev = (a, b) -> b.compareTo(a);
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>(rev);
        map.put(1, "a");
        map.put(3, "c");
        map.put(5, "e");
        check(map.higherKey(5) == 3, "reverse higherKey(5) should be 3");
        check(map.higherKey(3) == 1, "reverse higherKey(3) should be 1");
        check(map.higherKey(1) == null, "reverse higherKey(1) should be null");
    }
}
