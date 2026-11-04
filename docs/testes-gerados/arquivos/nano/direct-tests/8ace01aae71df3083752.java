/*
 * @test
 * @summary Test for Locale.getISOLanguages()
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testGetISOLanguages();
    }

    public static void testGetISOLanguages() {
        String[] languages = Locale.getISOLanguages();
        // Check that the returned array is not null
        if (languages == null) {
            throw new AssertionError("getISOLanguages() returned null");
        }
        // Check that the array is not empty
        if (languages.length == 0) {
            throw new AssertionError("getISOLanguages() returned empty array");
        }
        // Check that all elements are non-null and non-empty strings
        for (String lang : languages) {
            if (lang == null || lang.isEmpty()) {
                throw new AssertionError("Invalid language code: '" + lang + "'");
            }
        }
        // Check for presence of a known language code, e.g., "en"
        boolean containsEnglish = false;
        for (String lang : languages) {
            if ("en".equals(lang)) {
                containsEnglish = true;
                break;
            }
        }
        if (!containsEnglish) {
            throw new AssertionError("Language codes do not contain 'en'");
        }
        System.out.println("getISOLanguages() test passed.");
    }
}
