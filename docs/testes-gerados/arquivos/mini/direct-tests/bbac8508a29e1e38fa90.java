/*
 * @test
 * @summary Test ConcurrentHashMap constructor with initialCapacity
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        // Test with zero initial capacity (should be treated as 1 internally)
        ConcurrentHashMap<Object, Object> mapZero = new ConcurrentHashMap<>(0);
        if (mapZero == null) throw new RuntimeException("mapZero is null");

        // Test with small positive initial capacity
        ConcurrentHashMap<Object, Object> mapSmall = new ConcurrentHashMap<>(2);
        if (mapSmall == null) throw new RuntimeException("mapSmall is null");

        // Test with large initial capacity
        ConcurrentHashMap<Object, Object> mapLarge = new ConcurrentHashMap<>(1000);
        if (mapLarge == null) throw new RuntimeException("mapLarge is null");

        // Test with negative initial capacity - expect IllegalArgumentException
        boolean threw = false;
        try {
            new ConcurrentHashMap<>(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) throw new RuntimeException("Expected IllegalArgumentException for negative initialCapacity");
    }
}
