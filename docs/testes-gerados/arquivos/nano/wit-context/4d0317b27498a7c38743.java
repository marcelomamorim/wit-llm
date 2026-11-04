/*
 * @test
 * @summary Tests for StringLatin1.fillNull(byte[], int, int)
 * @run main StringLatin1WitupTest
 */

import java.lang.StringLatin1;
import java.util.Arrays;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testFillNull_EndGreaterThanIndex();
        testFillNull_EndLessThanIndex();
        testFillNull_EndEqualIndex();
    }

    private static void testFillNull_EndGreaterThanIndex() {
        byte[] array = {1, 2, 3, 4, 5};
        int index = 1;
        int end = 10; // end > array.length
        try {
            StringLatin1.fillNull(array, index, end);
            // Expect ArrayIndexOutOfBoundsException
            System.err.println("Failed: Expected ArrayIndexOutOfBoundsException for end > array.length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }

    private static void testFillNull_EndLessThanIndex() {
        byte[] array = {1, 2, 3, 4, 5};
        int index = 3;
        int end = 2; // end < index
        try {
            StringLatin1.fillNull(array, index, end);
            System.err.println("Failed: Expected IllegalArgumentException for end < index");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }

    private static void testFillNull_EndEqualIndex() {
        byte[] array = {1, 2, 3, 4, 5};
        int index = 2;
        int end = 2; // end == index
        try {
            StringLatin1.fillNull(array, index, end);
            // Should not throw, array remains unchanged
            if (array[2] != 0) {
                System.err.println("Failed: array[2] should be 0 after fillNull");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }
}
