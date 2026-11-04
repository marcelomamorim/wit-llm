/*
 * @test
 * @summary Verifies ConcurrentHashMap.remove(Object, Object) null-key NPE and value-matching behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testNullKeyThrowsNullPointerException();
        testNullValueReturnsFalse();
        testMatchingValueRemovesEntry();
        testMismatchedValueKeepsEntry();
        testAbsentKeyReturnsFalse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullKeyThrowsNullPointerException() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(1, 10);
        try {
            map.remove(null, Integer.valueOf(10));
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        check(Integer.valueOf(10).equals(map.get(1)), "entry must remain after null key failure");
    }

    static void testNullValueReturnsFalse() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(1, 10);
        check(!map.remove(Integer.valueOf(1), null), "remove with null value must return false");
        check(Integer.valueOf(10).equals(map.get(1)), "entry must remain when value is null");
    }

    static void testMatchingValueRemovesEntry() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(1, 10);
        check(map.remove(Integer.valueOf(1), Integer.valueOf(10)), "remove with matching value must return true");
        check(!map.containsKey(1), "entry must be removed when value matches");
    }

    static void testMismatchedValueKeepsEntry() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(1, 10);
        check(!map.remove(Integer.valueOf(1), Integer.valueOf(11)), "remove with mismatched value must return false");
        check(Integer.valueOf(10).equals(map.get(1)), "entry must remain when value mismatches");
    }

    static void testAbsentKeyReturnsFalse() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        check(!map.remove(Integer.valueOf(2), Integer.valueOf(10)), "remove on absent key must return false");
        check(map.isEmpty(), "map must stay empty");
    }
}
