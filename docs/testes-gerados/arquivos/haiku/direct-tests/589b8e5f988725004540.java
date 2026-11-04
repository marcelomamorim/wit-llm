/*
 * @test
 * @summary Verifies ConcurrentSkipListMap.higherKey returns the least key strictly greater than the argument, or null when none exists
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        check(map.higherKey(1) == null, "empty map higherKey must return null");
    }

    static void testNaturalOrderExactAndBetweenKeys() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");
        map.put(5, "e");
        check(map.higherKey(0).equals(1), "higherKey(0) should be 1");
        check(map.higherKey(1).equals(3), "higherKey(1) should be 3 (strictly greater)");
        check(map.higherKey(2).equals(3), "higherKey(2) should be 3");
        check(map.higherKey(3).equals(5), "higherKey(3) should be 5 (strictly greater)");
        check(map.higherKey(4).equals(5), "higherKey(4) should be 5");
    }

    static void testNoGreaterKeyReturnsNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");
        map.put(5, "e");
        check(map.higherKey(5) == null, "higherKey(max) must return null");
        check(map.higherKey(6) == null, "higherKey(above max) must return null");
    }

    static void testReverseComparatorOrder() {
        ConcurrentSkipListMap<Integer, String> map =
                new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        map.put(1, "a");
        map.put(3, "c");
        map.put(5, "e");
        check(map.higherKey(5).equals(3), "reverse order: higherKey(5) should be 3");
        check(map.higherKey(3).equals(1), "reverse order: higherKey(3) should be 1");
        check(map.higherKey(1) == null, "reverse order: higherKey(1) should be null");
    }

    static void testAfterRemoval() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");
        map.put(5, "e");
        map.remove(3);
        check(map.higherKey(1).equals(5), "after removing 3, higherKey(1) should be 5");
    }

    static void testStringKeys() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);
        check(map.higherKey("apple").equals("banana"), "higherKey(apple) should be banana");
        check(map.higherKey("avocado").equals("banana"), "higherKey(avocado) should be banana");
        check(map.higherKey("cherry") == null, "higherKey(cherry) should be null");
    }

    public static void main(String[] args) {
        testEmptyMap();
        testNaturalOrderExactAndBetweenKeys();
        testNoGreaterKeyReturnsNull();
        testReverseComparatorOrder();
        testAfterRemoval();
        testStringKeys();
    }
}
