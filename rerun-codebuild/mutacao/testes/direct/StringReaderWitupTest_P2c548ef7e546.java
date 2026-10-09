/* @test    @summary Test the mark method of StringReader for various readAheadLimit values    @run main StringReaderWitupTest_P2c548ef7e546 */

import java.io.StringReader;
import java.io.IOException;

public class StringReaderWitupTest_P2c548ef7e546 {

    public static void main(String[] args) {
        testMarkWithValidLimit();
        testMarkWithNegativeLimit();
        testMarkWithZeroLimit();
    }

    private static void testMarkWithValidLimit() {
        String s = "abc";
        StringReader sr = new StringReader(s);
        try {
            sr.mark(10);
            System.out.println("testMarkWithValidLimit passed");
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testMarkWithValidLimit", e);
        }
    }

    private static void testMarkWithNegativeLimit() {
        String s = "abc";
        StringReader sr = new StringReader(s);
        boolean exceptionThrown = false;
        try {
            sr.mark(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testMarkWithNegativeLimit", e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative readAheadLimit");
        } else {
            System.out.println("testMarkWithNegativeLimit passed");
        }
    }

    private static void testMarkWithZeroLimit() {
        String s = "abc";
        StringReader sr = new StringReader(s);
        try {
            sr.mark(0);
            System.out.println("testMarkWithZeroLimit passed");
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testMarkWithZeroLimit", e);
        }
    }
}
