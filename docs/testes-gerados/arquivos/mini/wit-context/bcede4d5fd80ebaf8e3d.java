/*
 * @test
 * @summary Test ResourceBundle.getFallbackLocale for null argument checks and normal return
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

        // Test that getFallbackLocale throws NullPointerException if baseName is null
        try {
            rb.getFallbackLocale(null, Locale.US);
            throw new RuntimeException("Expected NullPointerException for null baseName");
        } catch (NullPointerException expected) {
        }

        // Test that getFallbackLocale throws NullPointerException if locale is null
        try {
            rb.getFallbackLocale("baseName", null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
        }

        // Test that getFallbackLocale returns null for valid non-null arguments
        Locale fallback = rb.getFallbackLocale("baseName", Locale.US);
        if (fallback != null) {
            throw new RuntimeException("Expected null fallback locale for valid inputs");
        }
    }
}
