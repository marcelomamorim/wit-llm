/*
 * @test
 * @summary Test for Locale.getDisplayLanguage(Locale inLocale)
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testGetDisplayLanguageWithNull();
        testGetDisplayLanguageWithValidLocale();
        testGetDisplayLanguageWithDifferentLocale();
    }

    private static void testGetDisplayLanguageWithNull() {
        Locale locale = Locale.getDefault();
        try {
            locale.getDisplayLanguage(null);
            System.out.println("FAIL: Expected NullPointerException for null inLocale");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null inLocale");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null inLocale: " + t);
        }
    }

    private static void testGetDisplayLanguageWithValidLocale() {
        Locale locale = new Locale("en", "US");
        Locale targetLocale = new Locale("fr", "FR");
        String displayLanguage = locale.getDisplayLanguage(targetLocale);
        if (displayLanguage != null && !displayLanguage.isEmpty()) {
            System.out.println("PASS: getDisplayLanguage with valid locale returned: " + displayLanguage);
        } else {
            System.out.println("FAIL: getDisplayLanguage with valid locale returned null or empty");
        }
    }

    private static void testGetDisplayLanguageWithDifferentLocale() {
        Locale locale = new Locale("es");
        Locale targetLocale = new Locale("de");
        String displayLanguage = locale.getDisplayLanguage(targetLocale);
        if (displayLanguage != null && !displayLanguage.isEmpty()) {
            System.out.println("PASS: getDisplayLanguage with different locale returned: " + displayLanguage);
        } else {
            System.out.println("FAIL: getDisplayLanguage with different locale returned null or empty");
        }
    }
}
