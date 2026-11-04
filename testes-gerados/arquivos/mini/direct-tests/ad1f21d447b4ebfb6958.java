/*
 * @test
 * @summary Test ArrayList constructor with initialCapacity
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testPositiveCapacity();
        testZeroCapacity();
        testNegativeCapacity();
    }

    private static void testPositiveCapacity() {
        ArrayList<Object> list = new ArrayList<>(5);
        if (list.size() != 0) {
            throw new RuntimeException("Expected size 0 for new ArrayList with positive capacity");
        }
    }

    private static void testZeroCapacity() {
        ArrayList<Object> list = new ArrayList<>(0);
        if (list.size() != 0) {
            throw new RuntimeException("Expected size 0 for new ArrayList with zero capacity");
        }
    }

    private static void testNegativeCapacity() {
        try {
            new ArrayList<>(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative capacity");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
