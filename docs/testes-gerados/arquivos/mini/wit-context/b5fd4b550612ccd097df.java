/*
 * @test
 * @summary Test java.util.ResourceBundle.getFallbackLocale behavior and exception
 * @run main ResourceBundleWitupTest
 */

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {
    public static void main(String[] args) {
        testNullBaseNameThrowsNPE();
        testFallbackLocaleReturnsNullForDefaultLocale();
        testFallbackLocaleReturnsDefaultLocaleForOtherLocale();
    }

    private static void testNullBaseNameThrowsNPE() {
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

        try {
            rb.getFallbackLocale(null, Locale.US);
            throw new AssertionError("Expected NullPointerException for null baseName");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testFallbackLocaleReturnsNullForDefaultLocale() {
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

        Locale defaultLocale = Locale.getDefault();
        Locale result = rb.getFallbackLocale("baseName", defaultLocale);
        if (result != null) {
            throw new AssertionError("Expected null fallback locale when locale equals defaultLocale");
        }
    }

    private static void testFallbackLocaleReturnsDefaultLocaleForOtherLocale() {
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

        Locale defaultLocale = Locale.getDefault();
        Locale otherLocale = new Locale("fr", "FR");
        if (otherLocale.equals(defaultLocale)) {
            otherLocale = new Locale("de", "DE");
        }

        Locale result = rb.getFallbackLocale("baseName", otherLocale);
        if (!defaultLocale.equals(result)) {
            throw new AssertionError("Expected fallback locale to be defaultLocale when locale differs");
        }
    }
}
