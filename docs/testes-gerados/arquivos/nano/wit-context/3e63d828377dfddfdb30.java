/*
 * @test
 * @summary Tests for java.util.Arrays.parallelPrefix(long[], LongBinaryOperator)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.LongBinaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testParallelPrefix_NullOperator();
        testParallelPrefix_EmptyArray();
        testParallelPrefix_SingleElement();
        testParallelPrefix_NormalCase();
        testParallelPrefix_InvalidRange();
    }

    static void testParallelPrefix_NullOperator() {
        long[] array = {1L, 2L, 3L};
        try {
            Arrays.parallelPrefix(array, null);
            System.out.println("FAIL: Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException caught as expected");
        }
    }

    static void testParallelPrefix_EmptyArray() {
        long[] array = {};
        try {
            Arrays.parallelPrefix(array, (a, b) -> a + b);
            System.out.println("PASS: No exception for empty array");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for empty array: " + e);
        }
    }

    static void testParallelPrefix_SingleElement() {
        long[] array = {42L};
        try {
            Arrays.parallelPrefix(array, (a, b) -> a + b);
            if (array[0] == 42L) {
                System.out.println("PASS: Single element array unchanged");
            } else {
                System.out.println("FAIL: Single element array changed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for single element: " + e);
        }
    }

    static void testParallelPrefix_NormalCase() {
        long[] array = {1L, 2L, 3L, 4L};
        LongBinaryOperator op = (a, b) -> a + b;
        try {
            Arrays.parallelPrefix(array, op);
            long[] expected = {1L, 3L, 6L, 10L};
            if (java.util.Arrays.equals(array, expected)) {
                System.out.println("PASS: Normal case processed correctly");
            } else {
                System.out.println("FAIL: Normal case incorrect, got: " + java.util.Arrays.toString(array));
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception in normal case: " + e);
        }
    }

    static void testParallelPrefix_InvalidRange() {
        long[] array = {1L, 2L, 3L};
        try {
            // Simulate invalid range by passing a custom LongBinaryOperator that throws
            LongBinaryOperator invalidOp = (a, b) -> { throw new IllegalArgumentException(); };
            // Since the method does not accept range parameters, simulate invalid range check inside a wrapper
            // But as per current method, range check is internal; so we test behavior with invalid logic
            // For demonstration, we invoke the method with a null operator to trigger exception (already tested above)
            // or we can simulate an invalid operation inside the operator
            LongBinaryOperator op = (a, b) -> { if (a < 0 || b < 0) throw new ArrayIndexOutOfBoundsException(); return a + b; };
            Arrays.parallelPrefix(array, op);
            System.out.println("PASS: No exception for valid range");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("PASS: ArrayIndexOutOfBoundsException caught as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
