/* @test    @summary Tests for HashMap constructor with initialCapacity parameter    @run main HashMapWitupTest */

import java.util.HashMap;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testValidInitialCapacity();
        testNegativeInitialCapacity();
        testZeroInitialCapacity();
        testLargeInitialCapacity();
    }

    private static void testValidInitialCapacity() {
        HashMap<Integer, String> map = new HashMap<>(10);
        if (map == null) {
            throw new AssertionError("HashMap with valid initial capacity should not be null");
        }
    }

    private static void testNegativeInitialCapacity() {
        try {
            new HashMap<Integer, String>(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testZeroInitialCapacity() {
        try {
            HashMap<Integer, String> map = new HashMap<>(0);
            if (map == null) {
                throw new AssertionError("HashMap with zero initial capacity should not be null");
            }
        } catch (IllegalArgumentException e) {
            throw new AssertionError("HashMap constructor should not throw for zero initial capacity", e);
        }
    }

    private static void testLargeInitialCapacity() {
        HashMap<Integer, String> map = new HashMap<>(1000);
        if (map == null) {
            throw new AssertionError("HashMap with large initial capacity should not be null");
        }
    }
}
