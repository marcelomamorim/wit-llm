/*
 * @test
 * @summary Test WeakHashMap(int initialCapacity) constructor for valid and invalid inputs
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testValidInitialCapacity();
        testNegativeInitialCapacity();
    }

    private static void testValidInitialCapacity() {
        // Test with valid initial capacity values
        WeakHashMap<Object, Object> map1 = new WeakHashMap<>(0);
        if (map1 == null) throw new RuntimeException("WeakHashMap constructor returned null for capacity 0");

        WeakHashMap<Object, Object> map2 = new WeakHashMap<>(1);
        if (map2 == null) throw new RuntimeException("WeakHashMap constructor returned null for capacity 1");

        WeakHashMap<Object, Object> map3 = new WeakHashMap<>(16);
        if (map3 == null) throw new RuntimeException("WeakHashMap constructor returned null for capacity 16");

        WeakHashMap<Object, Object> map4 = new WeakHashMap<>(1 << 29); // large but valid capacity
        if (map4 == null) throw new RuntimeException("WeakHashMap constructor returned null for large capacity");
    }

    private static void testNegativeInitialCapacity() {
        // Test that constructor throws IllegalArgumentException for negative initialCapacity
        try {
            new WeakHashMap<Object, Object>(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative initialCapacity not thrown");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
