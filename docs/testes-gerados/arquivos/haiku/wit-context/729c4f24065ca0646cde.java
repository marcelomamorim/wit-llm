/*
 * @test
 * @summary Witup-generated checks for Bidi(String, int) constructor
 * @run main BidiWitupTest
 */

import java.text.Bidi;

public class BidiWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    public static void main(String[] args) {
        testNullParagraphThrowsIllegalArgumentException();
        testLeftToRightBaseLevel();
        testRightToLeftFlagGivesOddBaseLevel();
        testDefaultRightToLeftWithHebrewText();
        if (failures > 0) {
            throw new RuntimeException(failures + " failure(s)");
        }
    }

    static void testNullParagraphThrowsIllegalArgumentException() {
        boolean thrown = false;
        try {
            new Bidi((String) null, Bidi.DIRECTION_LEFT_TO_RIGHT);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "null paragraph must throw IllegalArgumentException");
    }

    static void testLeftToRightBaseLevel() {
        Bidi b = new Bidi("abc", Bidi.DIRECTION_LEFT_TO_RIGHT);
        check(b.isLeftToRight(), "ascii text with LTR flag must be left-to-right");
        check(b.getBaseLevel() == 0, "LTR base level must be 0");
    }

    static void testRightToLeftFlagGivesOddBaseLevel() {
        Bidi b = new Bidi("abc", Bidi.DIRECTION_RIGHT_TO_LEFT);
        check(b.getBaseLevel() == 1, "RTL flag must give base level 1");
    }

    static void testDefaultRightToLeftWithHebrewText() {
        Bidi b = new Bidi("\u05D0\u05D1", Bidi.DIRECTION_DEFAULT_RIGHT_TO_LEFT);
        check(b.getBaseLevel() == 1, "Hebrew text with default RTL flag must have base level 1");
        check(b.isRightToLeft(), "Hebrew text must be right-to-left");
        check(!b.isLeftToRight(), "Hebrew text must not be left-to-right");
    }
}
