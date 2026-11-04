/*
 * @test
 * @summary Test for Locale.getDisplayName(Locale inLocale)
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testGetDisplayNameWithNull();
        testGetDisplayNameWithValidLocale();
        testGetDisplayNameWithEmptyLocale();
    }

    private static void testGetDisplayNameWithNull() {
        Locale locale = Locale.getDefault();
        try {
            locale.getDisplayName(null);
            System.out.println("FAIL: Expected NullPointerException for null inLocale");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null inLocale");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null inLocale: " + e);
        }
    }

    private static void testGetDisplayNameWithValidLocale() {
        Locale locale = new Locale("en", "US");
        String displayName = locale.getDisplayName(locale);
        if (displayName != null && displayName.length() > 0) {
            System.out.println("PASS: getDisplayName with valid locale returned: " + displayName);
        } else {
            System.out.println("FAIL: getDisplayName with valid locale returned null or empty");
        }
    }

    private static void testGetDisplayNameWithEmptyLocale() {
        Locale emptyLocale = new Locale("");
        String displayName = emptyLocale.getDisplayName(emptyLocale);
        if (displayName != null) {
            System.out.println("PASS: getDisplayName with empty locale returned: " + displayName);
        } else {
            System.out.println("FAIL: getDisplayName with empty locale returned null");
        }
    }
}
