/*
 * @test
 * @summary Tests for java.util.Locale class focusing on the LanguageRange constructor.
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testLanguageRangeWithNull();
        testLanguageRangeWithValidString();
        testLanguageRangeWithEmptyString();
    }

    public static void testLanguageRangeWithNull() {
        try {
            Locale.LanguageRange lr = new Locale.LanguageRange(null);
            System.out.println("testLanguageRangeWithNull: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testLanguageRangeWithNull: PASSED");
        } catch (Exception e) {
            System.out.println("testLanguageRangeWithNull: FAILED - Unexpected exception: " + e);
        }
    }

    public static void testLanguageRangeWithValidString() {
        try {
            String validRange = "en";
            Locale.LanguageRange lr = new Locale.LanguageRange(validRange);
            if (lr != null && lr.getRange().equals(validRange)) {
                System.out.println("testLanguageRangeWithValidString: PASSED");
            } else {
                System.out.println("testLanguageRangeWithValidString: FAILED - Unexpected object state");
            }
        } catch (Exception e) {
            System.out.println("testLanguageRangeWithValidString: FAILED - Exception: " + e);
        }
    }

    public static void testLanguageRangeWithEmptyString() {
        try {
            String emptyRange = "";
            Locale.LanguageRange lr = new Locale.LanguageRange(emptyRange);
            if (lr != null && lr.getRange().equals(emptyRange)) {
                System.out.println("testLanguageRangeWithEmptyString: PASSED");
            } else {
                System.out.println("testLanguageRangeWithEmptyString: FAILED - Unexpected object state");
            }
        } catch (Exception e) {
            System.out.println("testLanguageRangeWithEmptyString: FAILED - Exception: " + e);
        }
    }
}
