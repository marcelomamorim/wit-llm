/* @test    @summary Test StringReader.mark() method for valid and invalid inputs    @run main StringReaderWitupTest_P5e52bc17ba99 */

import java.io.StringReader;
import java.io.IOException;

public class StringReaderWitupTest_P5e52bc17ba99 {

    public static void main(String[] args) {
        testMarkValid();
        testMarkInvalid();
    }

    private static void testMarkValid() {
        String s = "Hello, World!";
        StringReader sr = new StringReader(s);
        try {
            sr.mark(10);
            System.out.println("PASS: mark() with valid readAheadLimit");
        } catch (Exception e) {
            throw new AssertionError("FAIL: mark() threw exception on valid input", e);
        }
    }

    private static void testMarkInvalid() {
        String s = "Test";
        StringReader sr = new StringReader(s);
        try {
            sr.mark(-1);
            throw new AssertionError("FAIL: mark() did not throw on negative readAheadLimit");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: mark() threw IllegalArgumentException on negative readAheadLimit");
        } catch (Exception e) {
            throw new AssertionError("FAIL: mark() threw unexpected exception", e);
        }
    }
}
