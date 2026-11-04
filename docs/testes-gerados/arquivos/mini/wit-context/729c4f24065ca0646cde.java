/*
 * @test
 * @summary Test constructor Bidi(String paragraph, int flags) for null input and basic functionality
 * @run main BidiWitupTest
 */

import java.text.Bidi;

public class BidiWitupTest {
    public static void main(String[] args) {
        testNullParagraphThrows();
        testBasicLTRParagraph();
        testBasicRTLParagraph();
    }

    private static void testNullParagraphThrows() {
        try {
            new Bidi((String) null, Bidi.DIRECTION_LEFT_TO_RIGHT);
            throw new RuntimeException("Expected IllegalArgumentException for null paragraph");
        } catch (IllegalArgumentException e) {
            // Expected exception
            if (!"paragraph is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testBasicLTRParagraph() {
        String text = "abc def";
        Bidi bidi = new Bidi(text, Bidi.DIRECTION_LEFT_TO_RIGHT);
        if (bidi.isRightToLeft()) {
            throw new RuntimeException("Expected left-to-right text to not be right-to-left");
        }
        if (bidi.getLength() != text.length()) {
            throw new RuntimeException("Length mismatch: expected " + text.length() + " but got " + bidi.getLength());
        }
    }

    private static void testBasicRTLParagraph() {
        // Hebrew letters (right-to-left)
        String text = "\u05D0\u05D1\u05D2"; // Alef, Bet, Gimel
        Bidi bidi = new Bidi(text, Bidi.DIRECTION_RIGHT_TO_LEFT);
        if (!bidi.isRightToLeft()) {
            throw new RuntimeException("Expected right-to-left text to be right-to-left");
        }
        if (bidi.getLength() != text.length()) {
            throw new RuntimeException("Length mismatch: expected " + text.length() + " but got " + bidi.getLength());
        }
    }
}
