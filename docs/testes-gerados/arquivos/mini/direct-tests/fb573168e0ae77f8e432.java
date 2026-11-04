/*
 * @test
 * @summary Test java.net.URLConnection.getContent() method behavior
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetContentReturnsNonNull();
        testGetContentThrowsIOExceptionForInvalidURL();
    }

    private static void testGetContentReturnsNonNull() throws Exception {
        URL url = new URL("http://www.example.com/");
        URLConnection conn = url.openConnection();
        conn.connect();
        Object content = conn.getContent();
        if (content == null) {
            throw new AssertionError("getContent() returned null for a valid URL");
        }
    }

    private static void testGetContentThrowsIOExceptionForInvalidURL() {
        try {
            URL url = new URL("http://nonexistent.invalid.url/");
            URLConnection conn = url.openConnection();
            conn.getContent();
            throw new AssertionError("Expected IOException was not thrown for invalid URL");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
