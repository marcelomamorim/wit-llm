/* @test    @summary Test Bidi constructor with valid and null input    @run main BidiWitupTest */

import java.text.Bidi;

public class BidiWitupTest {

    public static void main(String[] args) {
        testValidParagraph();
        testNullParagraph();
    }

    static void testValidParagraph() {
        String paragraph = "Hello, world!";
        int flags = 0;
        Bidi bidi = new Bidi(paragraph, flags);
        if (bidi == null) {
            throw new AssertionError("Bidi instance should not be null for valid input");
        }
    }

    static void testNullParagraph() {
        boolean exceptionThrown = false;
        try {
            new Bidi(null, 0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for null paragraph");
        }
    }
}
