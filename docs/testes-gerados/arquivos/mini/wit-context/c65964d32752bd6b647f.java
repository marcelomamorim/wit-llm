/*
 * @test
 * @summary Test HashMap(int initialCapacity, float loadFactor) constructor for argument validation
 * @run main HashMapWitupTest
 */

import java.util.HashMap;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testNegativeInitialCapacity();
        testZeroLoadFactor();
        testNegativeLoadFactor();
        testNaNLoadFactor();
        testMaximumCapacityAdjustment();
        testValidParameters();
        System.out.println("All tests passed.");
    }

    private static void testNegativeInitialCapacity() {
        try {
            new HashMap(-1, 0.75f);
            throw new RuntimeException("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testZeroLoadFactor() {
        try {
            new HashMap(16, 0.0f);
            throw new RuntimeException("Expected IllegalArgumentException for zero loadFactor");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNegativeLoadFactor() {
        try {
            new HashMap(16, -0.1f);
            throw new RuntimeException("Expected IllegalArgumentException for negative loadFactor");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNaNLoadFactor() {
        try {
            new HashMap(16, Float.NaN);
            throw new RuntimeException("Expected IllegalArgumentException for NaN loadFactor");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testMaximumCapacityAdjustment() {
        // initialCapacity > MAXIMUM_CAPACITY should be capped to MAXIMUM_CAPACITY internally
        // MAXIMUM_CAPACITY is 1 << 30 (1073741824)
        // We cannot access threshold or internal state, so test no exception and normal behavior
        int aboveMax = 1073741825;
        HashMap<Object, Object> map = new HashMap<>(aboveMax, 0.75f);
        // Put and get to verify map works
        map.put("key", "value");
        if (!"value".equals(map.get("key"))) {
            throw new RuntimeException("Map failed to store and retrieve value after max capacity adjustment");
        }
    }

    private static void testValidParameters() {
        // Test some valid initialCapacity and loadFactor combinations
        HashMap<Object, Object> map1 = new HashMap<>(0, 0.75f);
        HashMap<Object, Object> map2 = new HashMap<>(16, 1.0f);
        HashMap<Object, Object> map3 = new HashMap<>(1024, 0.5f);

        // Basic put/get to verify map works
        map1.put("a", "A");
        map2.put("b", "B");
        map3.put("c", "C");

        if (!"A".equals(map1.get("a"))) {
            throw new RuntimeException("map1 failed put/get");
        }
        if (!"B".equals(map2.get("b"))) {
            throw new RuntimeException("map2 failed put/get");
        }
        if (!"C".equals(map3.get("c"))) {
            throw new RuntimeException("map3 failed put/get");
        }
    }
}
