/*
 * @test
 * @summary Test insert(int, double) for StringBuilder, focusing on exception paths.
 * @run main StringBuilderWitupTest
 */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithInvalidOffset();
        testInsertWithValidOffset();
    }

    private static void testInsertWithInvalidOffset() {
        StringBuilder sb = new StringBuilder("test");
        double value = 3.14;
        int invalidOffset1 = -1;
        int invalidOffset2 = 10; // greater than length
        try {
            sb.insert(invalidOffset1, value);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for offset " + invalidOffset1);
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            sb.insert(invalidOffset2, value);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for offset " + invalidOffset2);
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testInsertWithValidOffset() {
        StringBuilder sb = new StringBuilder("test");
        double value = 3.14;
        int validOffset = 2;
        StringBuilder result = sb.insert(validOffset, value);
        if (result.toString().equals("te3.143.14st")) {
            System.out.println("Passed: insert at valid offset");
        } else {
            System.out.println("Failed: Unexpected result: " + result.toString());
        }
    }
}
