/*
 * @test
 * @summary Test for StringReader.mark(int) method, focusing on exception path for invalid argument.
 * @run main StringReaderWitupTest_P2c548ef7e546
 */

import java.io.StringReader;
import java.io.IOException;

public class StringReaderWitupTest_P2c548ef7e546 {

    public static void main(String[] args) {
        testMarkWithNegativeReadAheadLimit();
        testMarkWithZeroReadAheadLimit();
        testMarkWithPositiveReadAheadLimit();
    }

    private static void testMarkWithNegativeReadAheadLimit() {
        StringReader sr = new StringReader("test");
        try {
            sr.mark(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative readAheadLimit");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative readAheadLimit");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testMarkWithZeroReadAheadLimit() {
        StringReader sr = new StringReader("test");
        try {
            sr.mark(0);
            System.out.println("PASS: mark(0) executed without exception");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for mark(0): " + e);
        }
    }

    private static void testMarkWithPositiveReadAheadLimit() {
        StringReader sr = new StringReader("test");
        try {
            sr.mark(10);
            System.out.println("PASS: mark(10) executed without exception");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for mark(10): " + e);
        }
    }
}
