/*
 * @test
 * @summary Test sun.net.www.URLConnection.addRequestProperty behavior
 * @run main URLConnectionWitupTest
 */

import sun.net.www.URLConnection;
import java.net.URL;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://example.com");
        URLConnection uc = new URLConnection(url) {
            @Override
            public void connect() {}
        };

        // Test that addRequestProperty throws NullPointerException if key is null
        try {
            uc.addRequestProperty(null, "value");
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            if (!"key is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + e.getMessage());
            }
        }

        // Test that addRequestProperty throws IllegalStateException if connected is true
        // connected is protected boolean in superclass java.net.URLConnection
        // We set it via reflection since no setter is exposed
        java.lang.reflect.Field connectedField = java.net.URLConnection.class.getDeclaredField("connected");
        connectedField.setAccessible(true);
        connectedField.setBoolean(uc, true);

        try {
            uc.addRequestProperty("key", "value");
            throw new RuntimeException("Expected IllegalStateException when connected is true");
        } catch (IllegalStateException e) {
            if (!"Already connected".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IllegalStateException message: " + e.getMessage());
            }
        }

        // Reset connected to false and test that no exception is thrown for valid key
        connectedField.setBoolean(uc, false);
        uc.addRequestProperty("key", "value");
    }
}
