/*
 * @test
 * @summary Tests for java.util.Arrays.sort(Object[], int, int)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testSortWithNegativeFromIndex();
        testSortWithFromIndexGreaterThanToIndex();
        testSortWithFromIndexEqualToToIndex();
    }

    private static void testSortWithNegativeFromIndex() {
        Object[] array = {1, 2, 3};
        try {
            Arrays.sort(array, -1, 2);
            // Expect ArrayIndexOutOfBoundsException
            System.out.println("Failed: Expected ArrayIndexOutOfBoundsException for negative fromIndex");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected ArrayIndexOutOfBoundsException for negative fromIndex");
        }
    }

    private static void testSortWithFromIndexGreaterThanToIndex() {
        Object[] array = {1, 2, 3};
        try {
            Arrays.sort(array, 2, 1);
            // Expect IllegalArgumentException
            System.out.println("Failed: Expected IllegalArgumentException for fromIndex > toIndex");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for fromIndex > toIndex");
        }
    }

    private static void testSortWithFromIndexEqualToToIndex() {
        Object[] array = {1, 2, 3};
        try {
            Arrays.sort(array, 1, 1);
            // No exception expected, array remains unchanged
            System.out.println("Passed: No exception for fromIndex == toIndex");
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception for fromIndex == toIndex: " + e);
        }
    }
}
