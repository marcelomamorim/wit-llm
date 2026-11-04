/*
 * @test
 * @summary Test java.util.Locale.getDisplayName(Locale) for null argument and basic locale
 * @run main java.util.LocaleWitupTest
 */

import java.util.Locale;
import java.text.MessageFormat;

public class LocaleWitupTest {
    public static void main(String[] args) {
        testGetDisplayNameWithNull();
        testGetDisplayNameBasic();
    }

    private static void testGetDisplayNameWithNull() {
        Locale locale = new Locale("en", "US");
        try {
            locale.getDisplayName(null);
            throw new RuntimeException("Expected NullPointerException for null inLocale");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testGetDisplayNameBasic() {
        Locale locale = new Locale("en", "US");
        Locale inLocale = new Locale("fr", "FR");
        String displayName = locale.getDisplayName(inLocale);
        if (displayName == null || displayName.isEmpty()) {
            throw new RuntimeException("getDisplayName returned null or empty string");
        }
        // The display name should contain the language name "anglais" or "English" in French locale
        // We do not assert exact string because it depends on resource bundles
        if (!displayName.toLowerCase(inLocale).contains("en") && !displayName.toLowerCase(inLocale).contains("anglais")) {
            throw new RuntimeException("getDisplayName does not contain expected language name: " + displayName);
        }
    }
}
