/*
 * @test
 * @summary Test for setRequestProperty method in URLConnection.
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetRequestPropertyConnected();
        testSetRequestPropertyNullKey();
    }

    private static void testSetRequestPropertyConnected() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = new URLConnection(url) {
            @Override
            public void connect() throws java.io.IOException {
                // no-op
            }
        };
        // Simulate connected state
        java.lang.reflect.Field connectedField = URLConnection.class.getDeclaredField("connected");
        connectedField.setAccessible(true);
        connectedField.setBoolean(conn, true);
        try {
            conn.setRequestProperty("Key", "Value");
            System.out.println("FAIL: Expected IllegalAccessError not thrown");
        } catch (IllegalAccessError e) {
            System.out.println("PASS: Caught expected IllegalAccessError");
        }
    }

    private static void testSetRequestPropertyNullKey() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = new URLConnection(url) {
            @Override
            public void connect() throws java.io.IOException {
                // no-op
            }
        };
        // Ensure not connected
        java.lang.reflect.Field connectedField = URLConnection.class.getDeclaredField("connected");
        connectedField.setAccessible(true);
        connectedField.setBoolean(conn, false);
        try {
            conn.setRequestProperty(null, "Value");
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException");
        }
    }
}
