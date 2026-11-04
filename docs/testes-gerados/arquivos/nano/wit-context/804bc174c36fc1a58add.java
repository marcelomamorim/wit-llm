/*
 * @test
 * @summary Test for URLConnection.getRequestProperty(String key) method.
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;
import java.net.URL;
import java.io.IOException;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws IOException {
        testGetRequestPropertyConnected();
        testGetRequestPropertyNotConnected();
    }

    private static void testGetRequestPropertyConnected() throws IOException {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        // Simulate connection established
        conn.connect();
        // Set a request property to test retrieval
        conn.setRequestProperty("Test-Header", "HeaderValue");
        // Mark as connected
        // Note: connect() is called, but actual internal state is not accessible; assume connected after connect()
        // Call getRequestProperty with existing key
        String value = conn.getRequestProperty("Test-Header");
        if (value == null || !value.equals("HeaderValue")) {
            throw new AssertionError("Expected 'HeaderValue', got: " + value);
        }
    }

    private static void testGetRequestPropertyNotConnected() throws IOException {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        // Do not connect
        // Set a request property
        conn.setRequestProperty("Another-Header", "AnotherValue");
        // Call getRequestProperty with key that was set
        String value = conn.getRequestProperty("Another-Header");
        if (value == null || !value.equals("AnotherValue")) {
            throw new AssertionError("Expected 'AnotherValue', got: " + value);
        }
    }
}
