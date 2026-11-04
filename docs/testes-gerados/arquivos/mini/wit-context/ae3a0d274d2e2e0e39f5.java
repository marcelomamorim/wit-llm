/*
 * @test
 * @summary Test HashSet(int initialCapacity) constructor for valid and invalid inputs
 * @run main HashSetWitupTest
 */

import java.util.HashSet;

public class HashSetWitupTest {
    public static void main(String[] args) {
        testValidInitialCapacity();
        testZeroInitialCapacity();
        testNegativeInitialCapacity();
        testLargeInitialCapacity();
        System.out.println("All tests passed.");
    }

    private static void testValidInitialCapacity() {
        // Typical valid initial capacity
        HashSet<String> set = new HashSet<>(16);
        if (set == null || set.size() != 0) {
            throw new AssertionError("HashSet with valid initial capacity failed");
        }
    }

    private static void testZeroInitialCapacity() {
        // Zero initial capacity is allowed and creates empty set
        HashSet<String> set = new HashSet<>(0);
        if (set == null || set.size() != 0) {
            throw new AssertionError("HashSet with zero initial capacity failed");
        }
    }

    private static void testNegativeInitialCapacity() {
        // Negative initial capacity should throw IllegalArgumentException
        try {
            new HashSet<>(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative initial capacity");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    private static void testLargeInitialCapacity() {
        // Very large initial capacity within allowed range
        int largeCapacity = (1 << 30) - 1; // max allowed is 1 << 30
        HashSet<String> set = new HashSet<>(largeCapacity);
        if (set == null || set.size() != 0) {
            throw new AssertionError("HashSet with large initial capacity failed");
        }
    }
}
