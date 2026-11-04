/*
 * @test
 * @summary Test ConcurrentHashMap constructor with initialCapacity and loadFactor
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        // Test with typical values
        ConcurrentHashMap<Object, Object> map1 = new ConcurrentHashMap<>(16, 0.75f);
        if (map1 == null) throw new AssertionError("map1 is null");

        // Test with zero initial capacity (should be accepted and adjusted internally)
        ConcurrentHashMap<Object, Object> map2 = new ConcurrentHashMap<>(0, 0.75f);
        if (map2 == null) throw new AssertionError("map2 is null");

        // Test with loadFactor exactly 1.0f
        ConcurrentHashMap<Object, Object> map3 = new ConcurrentHashMap<>(16, 1.0f);
        if (map3 == null) throw new AssertionError("map3 is null");

        // Test with loadFactor less than 0.0f (expect IllegalArgumentException)
        try {
            new ConcurrentHashMap<>(16, -0.1f);
            throw new AssertionError("Expected IllegalArgumentException for negative loadFactor");
        } catch (IllegalArgumentException expected) {
        }

        // Test with loadFactor zero (expect IllegalArgumentException)
        try {
            new ConcurrentHashMap<>(16, 0.0f);
            throw new AssertionError("Expected IllegalArgumentException for zero loadFactor");
        } catch (IllegalArgumentException expected) {
        }

        // Test with loadFactor NaN (expect IllegalArgumentException)
        try {
            new ConcurrentHashMap<>(16, Float.NaN);
            throw new AssertionError("Expected IllegalArgumentException for NaN loadFactor");
        } catch (IllegalArgumentException expected) {
        }

        System.out.println("All tests passed.");
    }
}
