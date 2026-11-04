/*
 * @test
 * @summary Test java.text.Bidi(String, int) constructor behavior
 * @run main BidiWitupTest
 */

import java.text.Bidi;

public class BidiWitupTest {
    public static void main(String[] args) {
        testNullParagraphThrows();
        testEmptyStringLTR();
        testSimpleLTR();
        testSimpleRTL();
        testDefaultDirectionFlags();
    }

    private static void testNullParagraphThrows() {
        try {
            new Bidi(null, Bidi.DIRECTION_LEFT_TO_RIGHT);
            throw new RuntimeException("Expected IllegalArgumentException for null paragraph");
        } catch (IllegalArgumentException e) {
            // expected
            if (!"paragraph is null".equals(e.getMessage())) {
                throw new RuntimeException("IllegalArgumentException message mismatch", e);
            }
        }
    }

    private static void testEmptyStringLTR() {
        Bidi bidi = new Bidi("", Bidi.DIRECTION_LEFT_TO_RIGHT);
        if (bidi.isRightToLeft()) {
            throw new RuntimeException("Empty string with LTR flag should not be RTL");
        }
    }

    private static void testSimpleLTR() {
        String text = "abc";
        Bidi bidi = new Bidi(text, Bidi.DIRECTION_LEFT_TO_RIGHT);
        if (bidi.isRightToLeft()) {
            throw new RuntimeException("Simple LTR text should not be RTL");
        }
    }

    private static void testSimpleRTL() {
        // Hebrew letters (right-to-left)
        String text = "\u05D0\u05D1\u05D2"; // Alef, Bet, Gimel
        Bidi bidi = new Bidi(text, Bidi.DIRECTION_RIGHT_TO_LEFT);
        if (!bidi.isRightToLeft()) {
            throw new RuntimeException("Simple RTL text should be RTL");
        }
    }

    private static void testDefaultDirectionFlags() {
        String text = "abc";
        Bidi bidiLtrDefault = new Bidi(text, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        if (bidiLtrDefault.isRightToLeft()) {
            throw new RuntimeException("Default LTR direction with LTR text should not be RTL");
        }

        Bidi bidiRtlDefault = new Bidi(text, Bidi.DIRECTION_DEFAULT_RIGHT_TO_LEFT);
        if (bidiRtlDefault.isRightToLeft()) {
            throw new RuntimeException("Default RTL direction with LTR text should not be RTL");
        }

        String rtlText = "\u05D0"; // Hebrew letter Alef
        Bidi bidiLtrDefaultRtlText = new Bidi(rtlText, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        if (!bidiLtrDefaultRtlText.isRightToLeft()) {
            throw new RuntimeException("Default LTR direction with RTL text should be RTL");
        }

        Bidi bidiRtlDefaultRtlText = new Bidi(rtlText, Bidi.DIRECTION_DEFAULT_RIGHT_TO_LEFT);
        if (!bidiRtlDefaultRtlText.isRightToLeft()) {
            throw new RuntimeException("Default RTL direction with RTL text should be RTL");
        }
    }
}