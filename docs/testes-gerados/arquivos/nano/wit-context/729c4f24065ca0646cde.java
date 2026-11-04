/* @test    @summary Test for Bidi constructor with null paragraph to verify IllegalArgumentException is thrown.    @run main BidiWitupTest */

import java.text.Bidi;

public class BidiWitupTest {

    public static void main(String[] args) {
        testNullParagraph();
    }

    public static void testNullParagraph() {
        boolean exceptionThrown = false;
        try {
            new Bidi(null, 0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException was not thrown for null paragraph.");
        }
    }
}
