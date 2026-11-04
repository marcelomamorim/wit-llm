/*
 * @test
 * @summary Test addRequestProperty method for exception paths
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testAddRequestProperty_ConnectedThrows();
        testAddRequestProperty_NullKeyThrows();
    }

    private static void testAddRequestProperty_ConnectedThrows() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = new URLConnection(url) {};
        // Simulate connected state
        java.lang.reflect.Field connectedField = URLConnection.class.getDeclaredField("connected");
        connectedField.setAccessible(true);
        connectedField.setBoolean(conn, true);
        try {
            conn.addRequestProperty("Key", "Value");
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException");
        }
    }

    private static void testAddRequestProperty_NullKeyThrows() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = new URLConnection(url) {};
        // Ensure not connected
        java.lang.reflect.Field connectedField = URLConnection.class.getDeclaredField("connected");
        connectedField.setAccessible(true);
        connectedField.setBoolean(conn, false);
        try {
            conn.addRequestProperty(null, "Value");
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException");
        }
    }
}
