/*
 * @test
 * @summary Test for java.net.URLConnection.getContent()
 * @run main URLConnectionWitupTest
 */

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetContentWithValidURL();
        testGetContentWithInvalidURL();
    }

    private static void testGetContentWithValidURL() {
        try {
            URL url = new URL("http://example.com");
            URLConnection connection = url.openConnection();
            Object content = connection.getContent();
            if (content == null) {
                throw new AssertionError("Expected non-null content for valid URL");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for valid URL", e);
        }
    }

    private static void testGetContentWithInvalidURL() {
        try {
            URL url = new URL("http://invalid.url");
            URLConnection connection = url.openConnection();
            connection.getContent();
            throw new AssertionError("Expected IOException for invalid URL");
        } catch (IOException e) {
            // Expected exception, test passes
        }
    }
}
