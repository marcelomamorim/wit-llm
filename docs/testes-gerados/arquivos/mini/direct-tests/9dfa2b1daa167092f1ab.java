/*
 * @test
 * @summary Test java.net.URLConnection.getContent(Class<?>[] classes) method
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Use a known URL that is always available (http://example.com)
        URL url = new URL("http://example.com/");
        URLConnection conn = url.openConnection();

        // Test getContent with null classes array (should not throw, but may return content)
        try {
            Object content = conn.getContent(null);
            if (content == null) {
                throw new RuntimeException("getContent(null) returned null");
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException thrown from getContent(null): " + e);
        }

        // Test getContent with empty classes array
        try {
            Object content = conn.getContent(new Class<?>[0]);
            if (content == null) {
                throw new RuntimeException("getContent(empty array) returned null");
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException thrown from getContent(empty array): " + e);
        }

        // Test getContent with classes array containing String.class
        try {
            Object content = conn.getContent(new Class<?>[] { String.class });
            if (content == null) {
                throw new RuntimeException("getContent(String.class) returned null");
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException thrown from getContent(String.class): " + e);
        }

        // Test getContent with classes array containing InputStream.class
        try {
            Object content = conn.getContent(new Class<?>[] { java.io.InputStream.class });
            if (content == null) {
                throw new RuntimeException("getContent(InputStream.class) returned null");
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException thrown from getContent(InputStream.class): " + e);
        }
    }
}
