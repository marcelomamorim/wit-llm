/*
 * @test
 * @summary Test for java.util.Arrays.parallelPrefix(int[] array, IntBinaryOperator op)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntBinaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testNullOperator();
        testEmptyArray();
        testValidOperation();
        testArrayIndexOutOfBounds();
        testIllegalArgument();
    }

    static void testNullOperator() {
        int[] arr = {1, 2, 3};
        try {
            Arrays.parallelPrefix(arr, null);
            System.out.println("Failed: Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException caught as expected");
        }
    }

    static void testEmptyArray() {
        int[] arr = {};
        try {
            Arrays.parallelPrefix(arr, (a, b) -> a + b);
            System.out.println("Passed: No exception for empty array");
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception for empty array: " + e);
        }
    }

    static void testValidOperation() {
        int[] arr = {1, 2, 3, 4};
        Arrays.parallelPrefix(arr, (a, b) -> a + b);
        if (Arrays.equals(arr, new int[]{1, 3, 6, 10})) {
            System.out.println("Passed: Valid operation test");
        } else {
            System.out.println("Failed: Unexpected array state: " + java.util.Arrays.toString(arr));
        }
    }

    static void testArrayIndexOutOfBounds() {
        int[] arr = {1, 2, 3};
        try {
            Arrays.parallelPrefix(arr, (a, b) -> a + b);
            // No index out of bounds expected in normal execution
            System.out.println("Passed: No ArrayIndexOutOfBoundsException in normal execution");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Failed: Unexpected ArrayIndexOutOfBoundsException: " + e);
        }
    }

    static void testIllegalArgument() {
        int[] arr = {1, 2, 3};
        try {
            Arrays.parallelPrefix(arr, (a, b) -> {
                if (a > b) throw new IllegalArgumentException();
                return a + b;
            });
            System.out.println("Passed: Custom operation with no exception");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed: Unexpected IllegalArgumentException: " + e);
        }
    }
}
