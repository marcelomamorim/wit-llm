/*
 * @test
 * @summary Test ResourceBundle.getTimeToLive(String, Locale) for null checks and default return
 * @run main ResourceBundleWitupTest
 */

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {
    public static void main(String[] args) {
        testGetTimeToLiveWithNullBaseName();
        testGetTimeToLiveWithNullLocale();
        testGetTimeToLiveWithValidArgs();
    }

    private static void testGetTimeToLiveWithNullBaseName() {
        try {
            new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    return null;
                }

                @Override
                public java.util.Enumeration<String> getKeys() {
                    return null;
                }
            }.getTimeToLive(null, Locale.US);
            throw new RuntimeException("Expected NullPointerException for null baseName");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testGetTimeToLiveWithNullLocale() {
        try {
            new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    return null;
                }

                @Override
                public java.util.Enumeration<String> getKeys() {
                    return null;
                }
            }.getTimeToLive("baseName", null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testGetTimeToLiveWithValidArgs() {
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
        long ttl = rb.getTimeToLive("baseName", Locale.US);
        if (ttl != ResourceBundle.TTL_NO_EXPIRATION_CONTROL) {
            throw new RuntimeException("Expected TTL_NO_EXPIRATION_CONTROL but got " + ttl);
        }
    }
}
