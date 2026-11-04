/*
 * @test
 * @summary Test ArrayList constructor with initialCapacity including boundary and invalid values
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testPositiveCapacity();
        testZeroCapacity();
        testNegativeCapacity();
    }

    private static void testPositiveCapacity() {
        // Test with positive initial capacity
        int capacity = 10;
        ArrayList<Object> list = new ArrayList<>(capacity);
        if (list.size() != 0) {
            throw new AssertionError("Expected size 0 for new ArrayList with positive capacity");
        }
    }

    private static void testZeroCapacity() {
        // Test with zero initial capacity
        ArrayList<Object> list = new ArrayList<>(0);
        if (list.size() != 0) {
            throw new AssertionError("Expected size 0 for new ArrayList with zero capacity");
        }
    }

    private static void testNegativeCapacity() {
        // Test with negative initial capacity - expect IllegalArgumentException
        try {
            new ArrayList<>(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
