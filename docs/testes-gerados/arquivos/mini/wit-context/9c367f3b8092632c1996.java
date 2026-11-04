/*
 * @test
 * @summary Test sun.net.www.URLConnection.getRequestProperty(String) behavior
 * @run main URLConnectionWitupTest
 */

import sun.net.www.URLConnection;
import java.net.URL;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a URLConnection instance with a dummy URL
        URL url = new URL("http://example.com");
        URLConnection uc = new URLConnection(url) {
            @Override
            public void connect() {
                // no-op
            }
        };

        // Initially connected is false, getRequestProperty should return null
        String result = uc.getRequestProperty("anyKey");
        if (result != null) {
            throw new RuntimeException("Expected null when not connected");
        }

        // Set connected to true via reflection to test exception throwing
        java.lang.reflect.Field connectedField = URLConnection.class.getDeclaredField("connected");
        connectedField.setAccessible(true);
        connectedField.setBoolean(uc, true);

        boolean threw = false;
        try {
            uc.getRequestProperty("anyKey");
        } catch (IllegalStateException e) {
            if (!"Already connected".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when connected is true");
        }
    }
}
