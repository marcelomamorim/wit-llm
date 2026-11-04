/*
 * @test
 * @summary Test for applyLocalizedPattern method in SimpleDateFormat
 * @run main SimpleDateFormatWitupTest
 */

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SimpleDateFormatWitupTest {

    public static void main(String[] args) {
        testApplyLocalizedPatternWithValidPattern();
        testApplyLocalizedPatternWithEmptyPattern();
        testApplyLocalizedPatternWithNullPattern();
    }

    private static void testApplyLocalizedPatternWithValidPattern() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            String pattern = "yyyy-MM-dd";
            sdf.applyLocalizedPattern(pattern);
            System.out.println("testApplyLocalizedPatternWithValidPattern: PASSED");
        } catch (Exception e) {
            System.out.println("testApplyLocalizedPatternWithValidPattern: FAILED - " + e);
        }
    }

    private static void testApplyLocalizedPatternWithEmptyPattern() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            String pattern = "";
            sdf.applyLocalizedPattern(pattern);
            System.out.println("testApplyLocalizedPatternWithEmptyPattern: PASSED");
        } catch (Exception e) {
            System.out.println("testApplyLocalizedPatternWithEmptyPattern: FAILED - " + e);
        }
    }

    private static void testApplyLocalizedPatternWithNullPattern() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            String pattern = null;
            sdf.applyLocalizedPattern(pattern);
            System.out.println("testApplyLocalizedPatternWithNullPattern: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testApplyLocalizedPatternWithNullPattern: PASSED");
        } catch (Exception e) {
            System.out.println("testApplyLocalizedPatternWithNullPattern: FAILED - " + e);
        }
    }
}
