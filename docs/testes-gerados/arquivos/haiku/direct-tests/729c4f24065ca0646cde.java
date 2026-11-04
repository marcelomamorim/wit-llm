/*
 * @test
 * @summary Tests public Bidi(String, int) constructor behavior
 * @run main BidiWitupTest
 */

import java.text.Bidi;

public class BidiWitupTest {

    public static void main(String[] args) {
        testNullParagraphThrows();
        testLeftToRightBase();
        testRightToLeftBase();
        testDefaultDirectionFromFirstStrongRtl();
        testMixedParagraph();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullParagraphThrows() {
        boolean thrown = false;
        try {
            new Bidi((String) null, Bidi.DIRECTION_LEFT_TO_RIGHT);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "null paragraph must throw IllegalArgumentException");
    }

    static void testLeftToRightBase() {
        Bidi bidi = new Bidi("abc", Bidi.DIRECTION_LEFT_TO_RIGHT);
        check(bidi.isLeftToRight(), "pure LTR text must be left-to-right");
        check(!bidi.isRightToLeft(), "pure LTR text must not be right-to-left");
        check(bidi.baseIsLeftToRight(), "base must be left-to-right");
        check(bidi.getBaseLevel() == 0, "LTR base level must be 0");
        check(bidi.getLength() == 3, "length must be 3");
        check(bidi.getRunCount() == 1, "pure LTR text must have one run");
        check(!bidi.isMixed(), "pure LTR text must not be mixed");
    }

    static void testRightToLeftBase() {
        Bidi bidi = new Bidi("\u05D0\u05D1", Bidi.DIRECTION_RIGHT_TO_LEFT);
        check(bidi.isRightToLeft(), "Hebrew text with RTL base must be right-to-left");
        check(!bidi.baseIsLeftToRight(), "base must be right-to-left");
        check(bidi.getBaseLevel() == 1, "RTL base level must be 1");
        check(bidi.getRunCount() == 1, "pure RTL text must have one run");
        check(bidi.getRunLevel(0) == 1, "RTL run level must be 1");
    }

    static void testDefaultDirectionFromFirstStrongRtl() {
        Bidi bidi = new Bidi("\u05D0\u05D1 abc", Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        check(!bidi.baseIsLeftToRight(), "first strong char is RTL, base must be RTL");
        check(bidi.getBaseLevel() == 1, "base level must be 1 for RTL first strong");
    }

    static void testMixedParagraph() {
        Bidi bidi = new Bidi("abc \u05D0", Bidi.DIRECTION_LEFT_TO_RIGHT);
        check(bidi.isMixed(), "LTR and RTL text must be mixed");
        check(bidi.getRunCount() == 2, "mixed text must have two runs");
        check(bidi.getRunStart(0) == 0, "first run must start at 0");
        check(bidi.getRunLimit(0) == 4, "first run must end at 4");
        check(bidi.getRunLevel(0) == 0, "first run must be level 0");
        check(bidi.getRunLevel(1) == 1, "second run must be level 1");
    }
}
