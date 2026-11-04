/*
 * @test
 * @summary Test java.net.URL openConnection() method basic behavior
 * @run main java.net.URLWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLWitupTest {
    public static void main(String[] args) throws Exception {
        testOpenConnectionHttp();
        testOpenConnectionFile();
    }

    private static void testOpenConnectionHttp() throws IOException {
        URL url = new URL("http://www.example.com");
        URLConnection conn = url.openConnection();
        if (conn == null) {
            throw new AssertionError("openConnection() returned null for http URL");
        }
        // Check that the URL in the connection matches the original URL
        if (!url.equals(conn.getURL())) {
            throw new AssertionError("URLConnection URL does not match original URL");
        }
    }

    private static void testOpenConnectionFile() throws IOException {
        URL url = new URL("file:/tmp");
        URLConnection conn = url.openConnection();
        if (conn == null) {
            throw new AssertionError("openConnection() returned null for file URL");
        }
        if (!url.equals(conn.getURL())) {
            throw new AssertionError("URLConnection URL does not match original URL");
        }
    }
}
