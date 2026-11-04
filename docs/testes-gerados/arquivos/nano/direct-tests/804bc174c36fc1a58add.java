/*
 * @test
 * @summary Test getRequestProperty method of URLConnection for various keys.
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws IOException {
        testGetRequestPropertyWithNullKey();
        testGetRequestPropertyWithEmptyKey();
        testGetRequestPropertyWithValidKey();
    }

    private static void testGetRequestPropertyWithNullKey() throws IOException {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        String result = conn.getRequestProperty(null);
        System.out.println("getRequestProperty(null): " + result);
        // Expect null or no exception, depending on implementation
    }

    private static void testGetRequestPropertyWithEmptyKey() throws IOException {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        String result = conn.getRequestProperty("");
        System.out.println("getRequestProperty(""): " + result);
        // Expect null or empty string, depending on implementation
    }

    private static void testGetRequestPropertyWithValidKey() throws IOException {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        // Set a request property to test retrieval
        conn.setRequestProperty("Test-Key", "Test-Value");
        String result = conn.getRequestProperty("Test-Key");
        System.out.println("getRequestProperty("Test-Key"): " + result);
        // Expect "Test-Value"
    }
}
