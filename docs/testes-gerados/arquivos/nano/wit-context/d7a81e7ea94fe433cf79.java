/*
 * @test
 * @summary Test for StringBuilder.insert(int, boolean) method, focusing on exception path.
 * @run main StringBuilderWitupTest
 */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertBooleanWithInvalidOffset();
        testInsertBooleanWithValidOffset();
    }

    private static void testInsertBooleanWithInvalidOffset() {
        StringBuilder sb = new StringBuilder("test");
        int invalidOffset = -1; // less than 0
        try {
            sb.insert(invalidOffset, true);
            System.err.println("Expected StringIndexOutOfBoundsException not thrown for offset " + invalidOffset);
            System.exit(1);
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
        invalidOffset = sb.length() + 1; // greater than current length
        try {
            sb.insert(invalidOffset, true);
            System.err.println("Expected StringIndexOutOfBoundsException not thrown for offset " + invalidOffset);
            System.exit(1);
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testInsertBooleanWithValidOffset() {
        StringBuilder sb = new StringBuilder("hello");
        int validOffset = 3;
        try {
            StringBuilder result = sb.insert(validOffset, true);
            if (result != sb) {
                System.err.println("Insert did not return the same StringBuilder instance");
                System.exit(1);
            }
            String expected = "heltrue lo";
            if (!sb.toString().equals(expected)) {
                System.err.println("Unexpected StringBuilder content: " + sb.toString());
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
