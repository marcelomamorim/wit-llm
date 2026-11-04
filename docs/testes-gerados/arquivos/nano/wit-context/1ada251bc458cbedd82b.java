/*
 * @test
 * @summary Test for StringBuffer.insert(int, long) method, focusing on exception behavior.
 * @run main StringBufferWitupTest
 */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertWithInvalidOffset();
        testInsertWithValidOffset();
    }

    private static void testInsertWithInvalidOffset() {
        StringBuffer sb = new StringBuffer("test");
        long value = 123L;
        int invalidOffsetNegative = -1;
        int invalidOffsetTooLarge = 10;
        try {
            sb.insert(invalidOffsetNegative, value);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected exception for negative offset");
        }
        try {
            sb.insert(invalidOffsetTooLarge, value);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for offset too large");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Passed: Caught expected exception for offset too large");
        }
    }

    private static void testInsertWithValidOffset() {
        StringBuffer sb = new StringBuffer("hello");
        long value = 456L;
        int validOffset = 3;
        try {
            StringBuffer result = sb.insert(validOffset, value);
            if (result == sb && sb.toString().equals("hel456lo")) {
                System.out.println("Passed: insert at valid offset produces expected result");
            } else {
                System.out.println("Failed: Unexpected result after insert at valid offset");
            }
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception at valid offset: " + e);
        }
    }
}
