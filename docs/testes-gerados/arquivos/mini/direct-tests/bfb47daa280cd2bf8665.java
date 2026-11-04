/*
 * @test
 * @summary Test java.util.Locale.getDisplayName(Locale) for various locale inputs
 * @run main java.util.LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {
    public static void main(String[] args) {
        // Test 1: Basic locale with language only
        Locale locale1 = new Locale("en");
        String displayName1 = locale1.getDisplayName(Locale.ENGLISH);
        if (displayName1 == null || displayName1.isEmpty()) {
            throw new RuntimeException("Test 1 failed: displayName should not be empty");
        }

        // Test 2: Locale with language and country
        Locale locale2 = new Locale("fr", "FR");
        String displayName2 = locale2.getDisplayName(Locale.FRENCH);
        if (displayName2 == null || displayName2.isEmpty()) {
            throw new RuntimeException("Test 2 failed: displayName should not be empty");
        }

        // Test 3: Locale with language, country and variant
        Locale locale3 = new Locale("ja", "JP", "JPVARIANT");
        String displayName3 = locale3.getDisplayName(Locale.JAPANESE);
        if (displayName3 == null || displayName3.isEmpty()) {
            throw new RuntimeException("Test 3 failed: displayName should not be empty");
        }

        // Test 4: Locale with empty language, country and script but with variant
        Locale locale4 = new Locale("", "", "VARIANTONLY");
        String displayName4 = locale4.getDisplayName(Locale.ENGLISH);
        if (displayName4 == null || displayName4.isEmpty()) {
            throw new RuntimeException("Test 4 failed: displayName should not be empty when variant present");
        }

        // Test 5: Locale with empty language, country, script and variant
        Locale locale5 = new Locale("", "", "");
        String displayName5 = locale5.getDisplayName(Locale.ENGLISH);
        if (displayName5 == null) {
            throw new RuntimeException("Test 5 failed: displayName should not be null");
        }
        if (!displayName5.isEmpty()) {
            throw new RuntimeException("Test 5 failed: displayName should be empty string when no language, country, script or variant");
        }

        // Test 6: Passing null as inLocale should behave as Locale.getDefault()
        // According to Locale API, null is not allowed, but the tested method does not explicitly check null
        // So test to see if it throws NullPointerException or returns a non-null string
        try {
            String displayName6 = locale1.getDisplayName(null);
            if (displayName6 == null) {
                throw new RuntimeException("Test 6 failed: displayName should not be null when inLocale is null");
            }
        } catch (NullPointerException npe) {
            // Acceptable if NPE is thrown
        }

        // Test 7: Locale with language and empty country and variant
        Locale locale7 = new Locale("de", "", "");
        String displayName7 = locale7.getDisplayName(Locale.GERMAN);
        if (displayName7 == null || displayName7.isEmpty()) {
            throw new RuntimeException("Test 7 failed: displayName should not be empty");
        }

        // Test 8: Locale with language, country and empty variant
        Locale locale8 = new Locale("es", "ES", "");
        String displayName8 = locale8.getDisplayName(Locale.forLanguageTag("es-ES"));
        if (displayName8 == null || displayName8.isEmpty()) {
            throw new RuntimeException("Test 8 failed: displayName should not be empty");
        }

        System.out.println("All tests passed.");
    }
}
