/*
 * @test
 * @summary Test java.util.Arrays.parallelPrefix(double[], DoubleBinaryOperator) for null operator and basic functionality
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;
import java.util.Objects;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullOperator();
        testEmptyArray();
        testSimpleArray();
    }

    private static void testNullOperator() {
        double[] array = {1.0, 2.0, 3.0};
        DoubleBinaryOperator op = null;
        try {
            Arrays.parallelPrefix(array, op);
            throw new RuntimeException("Expected NullPointerException for null operator");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testEmptyArray() {
        double[] array = new double[0];
        DoubleBinaryOperator op = new DoubleBinaryOperator() {
            public double applyAsDouble(double left, double right) {
                return left + right;
            }
        };
        // Should not throw and array remains empty
        Arrays.parallelPrefix(array, op);
        if (array.length != 0) {
            throw new RuntimeException("Empty array length changed");
        }
    }

    private static void testSimpleArray() {
        double[] array = {1.0, 2.0, 3.0, 4.0};
        DoubleBinaryOperator op = new DoubleBinaryOperator() {
            public double applyAsDouble(double left, double right) {
                return left + right;
            }
        };
        Arrays.parallelPrefix(array, op);
        // After parallelPrefix with sum operator, array should be prefix sums
        double[] expected = {1.0, 3.0, 6.0, 10.0};
        if (array.length != expected.length) {
            throw new RuntimeException("Array length changed after parallelPrefix");
        }
        for (int i = 0; i < array.length; i++) {
            if (Double.compare(array[i], expected[i]) != 0) {
                throw new RuntimeException("parallelPrefix result incorrect at index " + i + ": expected " + expected[i] + ", got " + array[i]);
            }
        }
    }
}
