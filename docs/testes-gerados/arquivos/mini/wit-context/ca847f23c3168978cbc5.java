/*
 * @test
 * @summary Test sun.net.www.URLConnection.setRequestProperty(String, String) for exceptions and normal behavior
 * @run main URLConnectionWitupTest
 */

import sun.net.www.URLConnection;
import java.net.URL;
import java.net.MalformedURLException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URLConnection subclass to instantiate abstract class
        class DummyURLConnection extends URLConnection {
            protected DummyURLConnection(URL u) {
                super(u);
            }
            public void connect() {}
        }

        URL url = new URL("http://example.com");
        DummyURLConnection conn = new DummyURLConnection(url);

        // Test 1: connected == true should throw IllegalAccessError
        try {
            // forcibly set connected to true via reflection since connected is protected in superclass
            java.lang.reflect.Field connectedField = java.net.URLConnection.class.getDeclaredField("connected");
            connectedField.setAccessible(true);
            connectedField.setBoolean(conn, true);

            try {
                conn.setRequestProperty("key", "value");
                throw new RuntimeException("Expected IllegalAccessError not thrown when connected is true");
            } catch (IllegalAccessError e) {
                // expected
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Reflection failed to set connected field", e);
        }

        // Reset connected to false
        java.lang.reflect.Field connectedField = java.net.URLConnection.class.getDeclaredField("connected");
        connectedField.setAccessible(true);
        connectedField.setBoolean(conn, false);

        // Test 2: key == null should throw NullPointerException
        try {
            conn.setRequestProperty(null, "value");
            throw new RuntimeException("Expected NullPointerException not thrown when key is null");
        } catch (NullPointerException e) {
            // expected
        }

        // Test 3: normal call sets property without exception
        conn.setRequestProperty("Test-Key", "Test-Value");
        String val = conn.getProperties().findValue("Test-Key");
        if (!"Test-Value".equals(val)) {
            throw new RuntimeException("Property value not set correctly");
        }
    }
}
