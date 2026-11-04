/*
 * @test
 * @summary Test java.util.Arrays.parallelPrefix(double[], DoubleBinaryOperator) basic and edge cases
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullOperator();
        testEmptyArray();
        testSingleElementArray();
        testMultipleElementsArray();
    }

    private static void testNullOperator() {
        double[] array = {1.0, 2.0};
        try {
            Arrays.parallelPrefix(array, null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testEmptyArray() {
        double[] array = new double[0];
        DoubleBinaryOperator op = (a, b) -> a + b;
        Arrays.parallelPrefix(array, op);
        if (array.length != 0) {
            throw new RuntimeException("Empty array length changed");
        }
    }

    private static void testSingleElementArray() {
        double[] array = {42.0};
        DoubleBinaryOperator op = (a, b) -> a + b;
        Arrays.parallelPrefix(array, op);
        if (array.length != 1 || array[0] != 42.0) {
            throw new RuntimeException("Single element array modified incorrectly");
        }
    }

    private static void testMultipleElementsArray() {
        double[] array = {1.0, 2.0, 3.0, 4.0};
        DoubleBinaryOperator op = (a, b) -> a + b;
        Arrays.parallelPrefix(array, op);
        double[] expected = {1.0, 3.0, 6.0, 10.0};
        if (array.length != expected.length) {
            throw new RuntimeException("Array length changed");
        }
        for (int i = 0; i < array.length; i++) {
            if (Double.compare(array[i], expected[i]) != 0) {
                throw new RuntimeException("parallelPrefix result incorrect at index " + i + ": expected " + expected[i] + ", got " + array[i]);
            }
        }
    }
}
