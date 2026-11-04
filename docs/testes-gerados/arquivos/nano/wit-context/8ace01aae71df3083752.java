/*
 * @test
 * @summary Test for Locale.getISOLanguages() method.
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testGetISOLanguages();
    }

    public static void testGetISOLanguages() {
        String[] result = null;
        try {
            result = Locale.getISOLanguages();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in getISOLanguages", e);
        }
        if (result == null) {
            throw new AssertionError("getISOLanguages() returned null");
        }
        if (result.length == 0) {
            throw new AssertionError("getISOLanguages() returned empty array");
        }
        // Check that all returned language codes are non-null and non-empty
        for (String lang : result) {
            if (lang == null || lang.isEmpty()) {
                throw new AssertionError("Invalid language code: " + lang);
            }
        }
        // Additional check: ensure known ISO language code is present
        boolean hasEnglish = false;
        for (String lang : result) {
            if ("en".equals(lang)) {
                hasEnglish = true;
                break;
            }
        }
        if (!hasEnglish) {
            throw new AssertionError("Expected ISO language code 'en' not found in result");
        }
    }
}
