/*
 * @test
 * @summary Test applyLocalizedPattern method of SimpleDateFormat for various patterns.
 * @run main SimpleDateFormatWitupTest
 */

import java.text.SimpleDateFormat;

public class SimpleDateFormatWitupTest {

    public static void main(String[] args) {
        testApplyLocalizedPatternBasic();
        testApplyLocalizedPatternWithSpecialChars();
        testApplyLocalizedPatternEmpty();
        testApplyLocalizedPatternNull();
    }

    static void testApplyLocalizedPatternBasic() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            String pattern = "yyyy-MM-dd";
            sdf.applyLocalizedPattern(pattern);
            if (!sdf.toPattern().equals(pattern)) {
                throw new AssertionError("Pattern mismatch: expected " + pattern + " but got " + sdf.toPattern());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed in testApplyLocalizedPatternBasic", e);
        }
    }

    static void testApplyLocalizedPatternWithSpecialChars() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            String pattern = "yyyy' Year' 'and' MM";
            sdf.applyLocalizedPattern(pattern);
            if (!sdf.toPattern().equals(pattern)) {
                throw new AssertionError("Pattern mismatch: expected " + pattern + " but got " + sdf.toPattern());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed in testApplyLocalizedPatternWithSpecialChars", e);
        }
    }

    static void testApplyLocalizedPatternEmpty() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            String pattern = "";
            sdf.applyLocalizedPattern(pattern);
            if (!sdf.toPattern().equals(pattern)) {
                throw new AssertionError("Pattern mismatch: expected empty pattern but got " + sdf.toPattern());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed in testApplyLocalizedPatternEmpty", e);
        }
    }

    static void testApplyLocalizedPatternNull() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyLocalizedPattern(null);
            throw new AssertionError("Expected NullPointerException for null pattern");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testApplyLocalizedPatternNull", e);
        }
    }
}
