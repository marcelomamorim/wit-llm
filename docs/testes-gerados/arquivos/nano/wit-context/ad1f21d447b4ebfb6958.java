/*
 * @test
 * @summary Test for ArrayList constructor with initialCapacity parameter.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testConstructorWithPositiveCapacity();
        testConstructorWithZeroCapacity();
        testConstructorWithNegativeCapacity();
    }

    private static void testConstructorWithPositiveCapacity() {
        int capacity = 10;
        ArrayList<Object> list = new ArrayList<>(capacity);
        if (list == null) {
            throw new AssertionError("ArrayList instance is null for positive capacity");
        }
        // Additional observable checks could include size, but size is 0 initially
        if (list.size() != 0) {
            throw new AssertionError("Expected size 0, but got " + list.size());
        }
    }

    private static void testConstructorWithZeroCapacity() {
        int capacity = 0;
        ArrayList<Object> list = new ArrayList<>(capacity);
        if (list == null) {
            throw new AssertionError("ArrayList instance is null for zero capacity");
        }
        if (list.size() != 0) {
            throw new AssertionError("Expected size 0, but got " + list.size());
        }
    }

    private static void testConstructorWithNegativeCapacity() {
        int capacity = -5;
        boolean exceptionThrown = false;
        try {
            ArrayList<Object> list = new ArrayList<>(capacity);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative capacity");
        }
    }
}
