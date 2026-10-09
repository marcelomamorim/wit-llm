/* @test    @summary Test the mark method of StringReader for valid and invalid arguments    @run main StringReaderWitupTest_P3ef880149cd1 */

import java.io.StringReader;
import java.io.IOException;

public class StringReaderWitupTest_P3ef880149cd1 {

    public static void main(String[] args) {
        testMarkValidLimit();
        testMarkNegativeLimit();
    }

    static void testMarkValidLimit() {
        String s = "Hello, World!";
        StringReader sr = new StringReader(s);
        try {
            sr.mark(10);
            if (sr == null) {
                throw new AssertionError("StringReader instance is null after mark");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in testMarkValidLimit", e);
        } catch (IllegalArgumentException e) {
            throw new AssertionError("IllegalArgumentException thrown for valid limit", e);
        }
    }

    static void testMarkNegativeLimit() {
        String s = "Test string";
        StringReader sr = new StringReader(s);
        try {
            sr.mark(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative limit not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in testMarkNegativeLimit", e);
        }
    }
}
