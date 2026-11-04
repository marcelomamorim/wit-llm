/* @test    @summary Test for String(byte[], int, int, Charset) method in java.lang.String    @run main StringWitupTest */

import java.lang.String;
import java.nio.charset.Charset;

public class StringWitupTest {

    public static void main(String[] args) {
        testNullCharset();
        testNegativeOffset();
        testOffsetExceedsArrayLength();
        testValidParameters();
    }

    private static void testNullCharset() {
        byte[] bytes = {65, 66, 67}; // "ABC"
        int offset = 0;
        int length = 3;
        Charset charset = null;
        try {
            new String(bytes, offset, length, charset);
            System.out.println("testNullCharset: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testNullCharset: PASSED");
        } catch (Throwable t) {
            System.out.println("testNullCharset: FAILED - Unexpected exception: " + t);
        }
    }

    private static void testNegativeOffset() {
        byte[] bytes = {65, 66, 67}; // "ABC"
        int offset = -1;
        int length = 2;
        Charset charset = Charset.forName("UTF-8");
        try {
            new String(bytes, offset, length, charset);
            System.out.println("testNegativeOffset: FAILED - Expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("testNegativeOffset: PASSED");
        } catch (Throwable t) {
            System.out.println("testNegativeOffset: FAILED - Unexpected exception: " + t);
        }
    }

    private static void testOffsetExceedsArrayLength() {
        byte[] bytes = {65, 66, 67}; // "ABC"
        int offset = 4;
        int length = 1;
        Charset charset = Charset.forName("UTF-8");
        try {
            new String(bytes, offset, length, charset);
            System.out.println("testOffsetExceedsArrayLength: FAILED - Expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("testOffsetExceedsArrayLength: PASSED");
        } catch (Throwable t) {
            System.out.println("testOffsetExceedsArrayLength: FAILED - Unexpected exception: " + t);
        }
    }

    private static void testValidParameters() {
        byte[] bytes = {65, 66, 67}; // "ABC"
        int offset = 0;
        int length = 3;
        Charset charset = Charset.forName("UTF-8");
        try {
            String s = new String(bytes, offset, length, charset);
            if ("ABC".equals(s)) {
                System.out.println("testValidParameters: PASSED");
            } else {
                System.out.println("testValidParameters: FAILED - Unexpected string: " + s);
            }
        } catch (Throwable t) {
            System.out.println("testValidParameters: FAILED - Unexpected exception: " + t);
        }
    }
}
