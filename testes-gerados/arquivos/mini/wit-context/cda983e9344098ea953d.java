/*
 * @test
 * @summary Test HashMap(int initialCapacity) constructor for valid and invalid inputs
 * @run main HashMapWitupTest
 */

import java.util.HashMap;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testValidInitialCapacity();
        testNegativeInitialCapacity();
    }

    private static void testValidInitialCapacity() {
        // Test with zero initial capacity (allowed)
        HashMap<Object, Object> mapZero = new HashMap<>(0);
        if (mapZero == null) {
            throw new AssertionError("HashMap constructor returned null for initialCapacity=0");
        }

        // Test with positive initial capacity
        HashMap<Object, Object> mapPositive = new HashMap<>(16);
        if (mapPositive == null) {
            throw new AssertionError("HashMap constructor returned null for initialCapacity=16");
        }

        // Test with large initial capacity within allowed range
        HashMap<Object, Object> mapLarge = new HashMap<>(1 << 20);
        if (mapLarge == null) {
            throw new AssertionError("HashMap constructor returned null for large initialCapacity");
        }
    }

    private static void testNegativeInitialCapacity() {
        try {
            new HashMap<>(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }

        try {
            new HashMap<>(Integer.MIN_VALUE);
            throw new AssertionError("Expected IllegalArgumentException for very negative initialCapacity");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
