/*
 * @test
 * @summary Test ResourceBundle.getFallbackLocale method behavior
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
        } catch (NullPointerException expected) {
        }

        // Test that passing null locale throws NullPointerException
        try {
            rb.getFallbackLocale("baseName", null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
        }

        // Test that valid inputs return null
        Locale result = rb.getFallbackLocale("baseName", Locale.US);
        if (result != null) {
            throw new RuntimeException("Expected null fallback locale for valid inputs");
        }
    }
}
