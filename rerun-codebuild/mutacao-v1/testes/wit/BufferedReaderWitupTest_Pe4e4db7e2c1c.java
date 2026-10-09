/* @test    @summary Test BufferedReader.mark() with valid and invalid inputs    @run main BufferedReaderWitupTest_Pe4e4db7e2c1c */

import java.io.BufferedReader;
import java.io.StringReader;

public class BufferedReaderWitupTest_Pe4e4db7e2c1c {

    public static void main(String[] args) {
        testMarkWithValidInput();
        testMarkWithNegativeInput();
    }

    private static void testMarkWithValidInput() {
        StringReader sr = new StringReader("line1\nline2");
        BufferedReader br = new BufferedReader(sr);
        try {
            br.mark(10);
            br.readLine();
            br.reset();
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid mark test: " + e);
        }
    }

    private static void testMarkWithNegativeInput() {
        StringReader sr = new StringReader("line1\nline2");
        BufferedReader br = new BufferedReader(sr);
        try {
            br.mark(-1);
            throw new AssertionError("Expected IllegalArgumentException not thrown for negative readAheadLimit");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
