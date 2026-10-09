/*
 * @test
 * @summary Test for StringReader.mark(int) method, focusing on IllegalArgumentException when readAheadLimit < 0.
 * @run main StringReaderWitupTest_P3ef880149cd1
 */

import java.io.StringReader;
import java.io.IOException;

public class StringReaderWitupTest_P3ef880149cd1 {

    public static void main(String[] args) {
        testMarkWithNegativeReadAheadLimit();
        testMarkWithValidReadAheadLimit();
        System.out.println("All tests passed.");
    }

    private static void testMarkWithNegativeReadAheadLimit() {
        String s = "example";
        StringReader sr = new StringReader(s);
        try {
            sr.mark(-1);
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    private static void testMarkWithValidReadAheadLimit() {
        String s = "another example";
        StringReader sr = new StringReader(s);
        try {
            sr.mark(10);
            // No exception expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }
}
