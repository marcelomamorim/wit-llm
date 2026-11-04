/*
 * @test
 * @summary Test for Locale.getDisplayScript(Locale inLocale)
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testGetDisplayScriptWithNull();
        testGetDisplayScriptWithValidLocale();
    }

    public static void testGetDisplayScriptWithNull() {
        Locale locale = new Locale("en", "US");
        try {
            locale.getDisplayScript(null);
            System.out.println("PASS: getDisplayScript(null) did not throw");
        } catch (NullPointerException e) {
            System.out.println("PASS: getDisplayScript(null) threw NullPointerException");
        } catch (Exception e) {
            System.out.println("FAIL: getDisplayScript(null) threw unexpected exception: " + e);
        }
    }

    public static void testGetDisplayScriptWithValidLocale() {
        Locale locale = new Locale("en", "US");
        try {
            String result = locale.getDisplayScript(locale);
            if (result != null) {
                System.out.println("PASS: getDisplayScript with valid locale returned: " + result);
            } else {
                System.out.println("FAIL: getDisplayScript with valid locale returned null");
            }
        } catch (Exception e) {
            System.out.println("FAIL: getDisplayScript with valid locale threw exception: " + e);
        }
    }
}
