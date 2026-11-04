/*
 * @test
 * @summary Test for Arrays.parallelPrefix(double[], DoubleBinaryOperator) null argument handling
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testParallelPrefixWithNullOperator();
        testParallelPrefixWithEmptyArray();
        testParallelPrefixWithValidInput();
    }

    private static void testParallelPrefixWithNullOperator() {
        double[] array = {1.0, 2.0, 3.0};
        try {
            Arrays.parallelPrefix(array, null);
            System.err.println("Failed: Expected NullPointerException for null operator");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    private static void testParallelPrefixWithEmptyArray() {
        double[] array = {};
        DoubleBinaryOperator op = (a, b) -> a + b;
        try {
            Arrays.parallelPrefix(array, op);
            // No exception expected, array remains empty
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for empty array: " + e);
        }
    }

    private static void testParallelPrefixWithValidInput() {
        double[] array = {1.0, 2.0, 3.0};
        DoubleBinaryOperator op = (a, b) -> a + b;
        try {
            Arrays.parallelPrefix(array, op);
            if (array.length != 3 || array[0] != 1.0 || array[1] != 3.0 || array[2] != 6.0) {
                System.err.println("Failed: Unexpected array result: " + java.util.Arrays.toString(array));
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception during valid input test: " + e);
        }
    }
}
