/* @test    @summary Test setRequestProperty method of URLConnection for various scenarios    @run main URLConnectionWitupTest */

import java.net.URL;
import sun.net.www.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetRequestPropertyWithNullKey();
        testSetRequestPropertyWhenConnected();
        testSetRequestPropertyWithValidKeyAndValue();
    }

    static void testSetRequestPropertyWithNullKey() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = new URLConnection(url) {};
        try {
            conn.setRequestProperty(null, "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testSetRequestPropertyWhenConnected() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = new URLConnection(url) {};
        // Simulate connection established
        java.lang.reflect.Field connectedField = URLConnection.class.getDeclaredField("connected");
        connectedField.setAccessible(true);
        connectedField.setBoolean(conn, true);
        try {
            conn.setRequestProperty("Key", "Value");
            throw new AssertionError("Expected IllegalAccessError when connected");
        } catch (IllegalAccessError e) {
            // expected
        }
    }

    static void testSetRequestPropertyWithValidKeyAndValue() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = new URLConnection(url) {};
        // Ensure not connected
        java.lang.reflect.Field connectedField = URLConnection.class.getDeclaredField("connected");
        connectedField.setAccessible(true);
        connectedField.setBoolean(conn, false);
        conn.setRequestProperty("Test-Key", "Test-Value");
        // Verify that property was set
        java.lang.reflect.Field propertiesField = URLConnection.class.getDeclaredField("properties");
        propertiesField.setAccessible(true);
        Object properties = propertiesField.get(conn);
        java.lang.reflect.Method findValueMethod = properties.getClass().getMethod("findValue", String.class);
        String value = (String) findValueMethod.invoke(properties, "Test-Key");
        if (!"Test-Value".equals(value)) {
            throw new AssertionError("Property value not set correctly");
        }
    }
}
