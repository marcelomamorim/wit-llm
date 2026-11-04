/*
 * @test
 * @summary Tests for java.util.Arrays.fill(int[], int, int, int)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillWithValidRange();
        testFillWithFromIndexLessThanZero();
        testFillWithFromIndexGreaterThanToIndex();
        testFillWithToIndexGreaterThanArrayLength();
    }

    static void testFillWithValidRange() {
        int[] array = {1, 2, 3, 4, 5};
        Arrays.fill(array, 1, 4, 99);
        if (array[0] != 1 || array[1] != 99 || array[2] != 99 || array[3] != 99 || array[4] != 5) {
            throw new AssertionError("testFillWithValidRange failed");
        }
    }

    static void testFillWithFromIndexLessThanZero() {
        int[] array = {1, 2, 3};
        try {
            Arrays.fill(array, -1, 2, 99);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testFillWithFromIndexGreaterThanToIndex() {
        int[] array = {1, 2, 3};
        try {
            Arrays.fill(array, 2, 1, 99);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testFillWithToIndexGreaterThanArrayLength() {
        int[] array = {1, 2, 3};
        try {
            Arrays.fill(array, 0, 4, 99);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
