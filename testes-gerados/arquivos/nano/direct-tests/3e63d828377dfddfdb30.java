/* @test    @summary Test Arrays.parallelPrefix with LongBinaryOperator    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.function.LongBinaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testParallelPrefixSum();
        testParallelPrefixProduct();
        testParallelPrefixWithNullOperator();
        testParallelPrefixEmptyArray();
    }

    static void testParallelPrefixSum() {
        long[] array = {1L, 2L, 3L, 4L};
        LongBinaryOperator sumOp = LongBinaryOperator { (a, b) -> a + b };
        long[] expected = {1L, 3L, 6L, 10L};
        Arrays.parallelPrefix(array, sumOp);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != expected[i]) {
                throw new AssertionError("Sum prefix failed at index " + i + ": expected " + expected[i] + " but got " + array[i]);
            }
        }
    }

    static void testParallelPrefixProduct() {
        long[] array = {1L, 2L, 3L, 4L};
        LongBinaryOperator productOp = LongBinaryOperator { (a, b) -> a * b };
        long[] expected = {1L, 2L, 6L, 24L};
        Arrays.parallelPrefix(array, productOp);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != expected[i]) {
                throw new AssertionError("Product prefix failed at index " + i + ": expected " + expected[i] + " but got " + array[i]);
            }
        }
    }

    static void testParallelPrefixWithNullOperator() {
        long[] array = {1L, 2L};
        try {
            Arrays.parallelPrefix(array, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testParallelPrefixEmptyArray() {
        long[] array = {};
        LongBinaryOperator sumOp = LongBinaryOperator { (a, b) -> a + b };
        Arrays.parallelPrefix(array, sumOp);
        if (array.length != 0) {
            throw new AssertionError("Empty array modified");
        }
    }
}
