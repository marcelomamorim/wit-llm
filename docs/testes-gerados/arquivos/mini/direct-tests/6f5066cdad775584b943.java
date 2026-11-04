/*
 * @test
 * @summary Test java.net.URL.getContent() method for basic functionality and exception handling
 * @run main java.net.URLWitupTest
 */

import java.net.URL;
import java.io.IOException;

public class URLWitupTest {
    public static void main(String[] args) throws Exception {
        testGetContentHttp();
        testGetContentFile();
        testGetContentMalformedURL();
    }

    private static void testGetContentHttp() throws Exception {
        URL url = new URL("http://example.com");
        Object content = url.getContent();
        if (content == null) {
            throw new RuntimeException("getContent() returned null for http URL");
        }
    }

    private static void testGetContentFile() throws Exception {
        // Using a file URL pointing to a known existing file (using current directory)
        String userDir = System.getProperty("user.dir");
        URL url = new URL("file", "", userDir);
        Object content = url.getContent();
        if (content == null) {
            throw new RuntimeException("getContent() returned null for file URL");
        }
    }

    private static void testGetContentMalformedURL() {
        try {
            // This URL is malformed and should throw MalformedURLException on construction
            new URL("://badurl").getContent();
            throw new RuntimeException("Expected java.net.MalformedURLException was not thrown");
        } catch (java.net.MalformedURLException e) {
            // Expected exception
        } catch (IOException e) {
            // IOException can be thrown by getContent, but here construction should fail first
            throw new RuntimeException("Unexpected IOException instead of MalformedURLException", e);
        }
    }
}
