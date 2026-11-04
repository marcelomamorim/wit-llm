/*
 * @test
 * @summary Test java.util.ResourceBundle.getFallbackLocale behavior
 * @run main ResourceBundleWitupTest
 */

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {
    public static void main(String[] args) {
        ResourceBundle rb = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                return null;
            }

            @Override
            public java.util.Enumeration<String> getKeys() {
                return null;
            }
        };

        // Test that passing null baseName throws NullPointerException
        try {
            rb.getFallbackLocale(null, Locale.US);
            throw new RuntimeException("Expected NullPointerException for null baseName");
        } catch (NullPointerException e) {
            // expected
        }

        // Test that if locale equals default locale, returns null
        Locale defaultLocale = Locale.getDefault();
        Locale result1 = rb.getFallbackLocale("baseName", defaultLocale);
        if (result1 != null) {
            throw new RuntimeException("Expected null fallback locale when locale equals default locale");
        }

        // Test that if locale differs from default locale, returns default locale
        Locale otherLocale = new Locale("fr", "FR");
        if (otherLocale.equals(defaultLocale)) {
            // unlikely but just in case, pick another locale
            otherLocale = new Locale("de", "DE");
        }
        Locale result2 = rb.getFallbackLocale("baseName", otherLocale);
        if (!defaultLocale.equals(result2)) {
            throw new RuntimeException("Expected fallback locale to be default locale when locale differs");
        }
    }
}
