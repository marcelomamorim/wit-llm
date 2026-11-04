/*
 * @test
 * @summary WITUP-derived checks for WeakHashMap(int initialCapacity) constructor: negative capacity rejection and observable behavior for valid capacities
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNegativeCapacityThrowsIllegalArgumentException();
        testMinimalNegativeCapacityThrows();
        testZeroCapacityIsAcceptedAndUsable();
        testSmallPositiveCapacityIsUsable();
        testNullKeyAndNullValueWithExplicitCapacity();
        System.out.println("PASSED");
    }

    static void testNegativeCapacityThrowsIllegalArgumentException() {
        try {
            new WeakHashMap<String, String>(-1);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for initialCapacity -1");
    }

    static void testMinimalNegativeCapacityThrows() {
        try {
            new WeakHashMap<String, String>(Integer.MIN_VALUE);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for Integer.MIN_VALUE");
    }

    static void testZeroCapacityIsAcceptedAndUsable() {
        WeakHashMap<String, String> map = new WeakHashMap<String, String>(0);
        String key = new String("zero-key");
        map.put(key, "value");
        check("value".equals(map.get(key)), "get after put should return stored value");
        check(map.size() == 1, "size should be 1 after one put");
        check(!map.isEmpty(), "map should not be empty after put");
    }

    static void testSmallPositiveCapacityIsUsable() {
        WeakHashMap<String, Integer> map = new WeakHashMap<String, Integer>(4);
        String k1 = new String("k1");
        String k2 = new String("k2");
        map.put(k1, Integer.valueOf(1));
        map.put(k2, Integer.valueOf(2));
        check(map.size() == 2, "size should be 2");
        check(Integer.valueOf(1).equals(map.get(k1)), "k1 mapping mismatch");
        check(Integer.valueOf(2).equals(map.get(k2)), "k2 mapping mismatch");
        check(map.containsKey(k1), "containsKey should be true for live key");
    }

    static void testNullKeyAndNullValueWithExplicitCapacity() {
        WeakHashMap<String, String> map = new WeakHashMap<String, String>(8);
        map.put(null, "nullKeyValue");
        check("nullKeyValue".equals(map.get(null)), "null key should be supported");
        String k = new String("nv");
        map.put(k, null);
        check(map.containsKey(k), "key with null value should be present");
        check(map.get(k) == null, "null value should be returned as null");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
