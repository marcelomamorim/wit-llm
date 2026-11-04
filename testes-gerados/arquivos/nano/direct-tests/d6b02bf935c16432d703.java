/*
 * @test
 * @summary Test getDisplayScript method of Locale class for various locales
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testGetDisplayScriptWithNull();
        testGetDisplayScriptWithEnglish();
        testGetDisplayScriptWithSpecificLocale();
        testGetDisplayScriptWithDifferentLocale();
    }

    static void testGetDisplayScriptWithNull() {
        Locale locale = new Locale("en", "US", "");
        String result = locale.getDisplayScript(null);
        System.out.println("getDisplayScript with null locale: " + result);
        // Expecting a non-null String, possibly empty or script name
        if (result == null) {
            throw new AssertionError("Result should not be null when input locale is null");
        }
    }

    static void testGetDisplayScriptWithEnglish() {
        Locale locale = new Locale("en", "US", "");
        String result = locale.getDisplayScript(Locale.ENGLISH);
        System.out.println("getDisplayScript with Locale.ENGLISH: " + result);
        if (result == null) {
            throw new AssertionError("Result should not be null for Locale.ENGLISH");
        }
    }

    static void testGetDisplayScriptWithSpecificLocale() {
        Locale locale = new Locale("zh", "CN", "");
        String result = locale.getDisplayScript(Locale.CHINESE);
        System.out.println("getDisplayScript with Chinese locale: " + result);
        if (result == null) {
            throw new AssertionError("Result should not be null for Chinese locale");
        }
    }

    static void testGetDisplayScriptWithDifferentLocale() {
        Locale locale = new Locale("fr", "FR", "");
        String result = locale.getDisplayScript(Locale.FRENCH);
        System.out.println("getDisplayScript with French locale: " + result);
        if (result == null) {
            throw new AssertionError("Result should not be null for French locale");
        }
    }
}
