/*
 * @test
 * @summary Test java.net.URLConnection.getRequestProperty(String) for expected behavior and exception
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetRequestPropertyBeforeConnect();
        testGetRequestPropertyWithNullKey();
    }

    private static void testGetRequestPropertyBeforeConnect() throws IOException {
        // Create a URLConnection instance using a valid URL
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();

        // Before connect(), getRequestProperty should throw IllegalStateException due to checkConnected()
        boolean threw = false;
        try {
            conn.getRequestProperty("someKey");
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when calling getRequestProperty before connect");
        }
    }

    private static void testGetRequestPropertyWithNullKey() throws IOException {
        // Create a URLConnection instance and connect
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        conn.connect();

        // After connect(), getRequestProperty with null key returns null (no exception expected)
        String result = conn.getRequestProperty(null);
        if (result != null) {
            throw new AssertionError("Expected null when getRequestProperty called with null key");
        }
    }
}
