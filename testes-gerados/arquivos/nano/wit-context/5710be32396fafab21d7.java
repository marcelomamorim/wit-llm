/*
 * @test
 * @summary Tests for java.util.Locale.setDefault(Locale)
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testSetDefaultWithNullLocale();
        testSetDefaultWithValidLocale();
    }

    private static void testSetDefaultWithNullLocale() {
        try {
            Locale.setDefault((Locale) null);
            System.out.println("testSetDefaultWithNullLocale: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testSetDefaultWithNullLocale: PASSED");
        } catch (Exception e) {
            System.out.println("testSetDefaultWithNullLocale: FAILED - Unexpected exception: " + e);
        }
    }

    private static void testSetDefaultWithValidLocale() {
        Locale originalDefault = Locale.getDefault();
        Locale newLocale = new Locale("en", "US");
        try {
            Locale.setDefault(newLocale);
            if (Locale.getDefault().equals(newLocale)) {
                System.out.println("testSetDefaultWithValidLocale: PASSED");
            } else {
                System.out.println("testSetDefaultWithValidLocale: FAILED - Locale not set correctly");
            }
        } catch (Exception e) {
            System.out.println("testSetDefaultWithValidLocale: FAILED - Exception: " + e);
        } finally {
            // Restore original default locale
            Locale.setDefault(originalDefault);
        }
    }
}
