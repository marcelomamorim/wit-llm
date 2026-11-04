/*
 * @test
 * @summary Test java.lang.StringLatin1.fillNull method for exception and normal behavior
 * @run main StringLatin1WitupTest
 */

import java.util.Arrays;

public class StringLatin1WitupTest {
    public static void main(String[] args) {
        testFillNullNormal();
        testFillNullIndexNegative();
        testFillNullEndLessThanIndex();
        testFillNullEndExceedsLength();
    }

    private static void testFillNullNormal() {
        byte[] arr = new byte[] {1, 2, 3, 4, 5};
        // fill from index 1 to 4 with zero
        java.lang.StringLatin1.fillNull(arr, 1, 4);
        // expected arr = {1,0,0,0,5}
        if (arr[0] != 1 || arr[1] != 0 || arr[2] != 0 || arr[3] != 0 || arr[4] != 5) {
            throw new RuntimeException("fillNull normal case failed: " + java.util.Arrays.toString(arr));
        }
    }

    private static void testFillNullIndexNegative() {
        byte[] arr = new byte[] {1, 2, 3};
        try {
            java.lang.StringLatin1.fillNull(arr, -1, 2);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testFillNullEndLessThanIndex() {
        byte[] arr = new byte[] {1, 2, 3};
        try {
            java.lang.StringLatin1.fillNull(arr, 2, 1);
            throw new RuntimeException("Expected IllegalArgumentException for end < index");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testFillNullEndExceedsLength() {
        byte[] arr = new byte[] {1, 2, 3};
        try {
            java.lang.StringLatin1.fillNull(arr, 1, 5);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for end > val.length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
