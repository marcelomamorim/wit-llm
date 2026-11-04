/*
 * @test
 * @summary Tests for StringLatin1.inflate(byte[], int, int)
 * @run main StringLatin1WitupTest
 */

import java.lang.StringLatin1;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testInflateWithNegativeLength();
        testInflateWithExcessiveLength();
    }

    private static void testInflateWithNegativeLength() {
        byte[] input = {1, 2, 3};
        int off = 0;
        int len = -1;
        try {
            byte[] result = StringLatin1.inflate(input, off, len);
            System.out.println("FAIL: Expected NegativeArraySizeException for negative length");
        } catch (NegativeArraySizeException e) {
            System.out.println("PASS: Caught expected NegativeArraySizeException");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testInflateWithExcessiveLength() {
        byte[] input = {1, 2, 3};
        int off = 0;
        int len = Integer.MAX_VALUE;
        try {
            byte[] result = StringLatin1.inflate(input, off, len);
            System.out.println("FAIL: Expected OutOfMemoryError for excessive length");
        } catch (OutOfMemoryError e) {
            System.out.println("PASS: Caught expected OutOfMemoryError");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
