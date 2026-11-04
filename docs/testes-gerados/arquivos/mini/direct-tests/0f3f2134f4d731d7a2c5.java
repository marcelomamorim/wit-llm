/*
 * @test
 * @summary Test java.util.ResourceBundle.getTimeToLive(String, Locale) behavior
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

        // Test 1: Normal call with non-null baseName and locale
        long ttl = rb.getTimeToLive("baseName", Locale.US);
        if (ttl != ResourceBundle.TTL_NO_EXPIRATION_CONTROL) {
            throw new AssertionError("Expected TTL_NO_EXPIRATION_CONTROL but got " + ttl);
        }

        // Test 2: baseName null should throw NullPointerException
        try {
            rb.getTimeToLive(null, Locale.US);
            throw new AssertionError("Expected NullPointerException for null baseName");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 3: locale null should throw NullPointerException
        try {
            rb.getTimeToLive("baseName", null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
