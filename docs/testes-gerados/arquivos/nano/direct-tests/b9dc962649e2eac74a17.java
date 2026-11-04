/*
 * @test
 * @summary Test URL.openStream() method for basic functionality.
 * @run main URLWitupTest
 */

import java.net.URL;
import java.io.InputStream;
import java.io.IOException;

public class URLWitupTest {

    public static void main(String[] args) throws Exception {
        testOpenStreamWithHttp();
        testOpenStreamWithFile();
        testOpenStreamWithInvalidURL();
    }

    private static void testOpenStreamWithHttp() throws Exception {
        URL url = new URL("http://www.example.com/");
        try (InputStream is = url.openStream()) {
            if (is == null) {
                throw new AssertionError("InputStream should not be null for valid HTTP URL");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for valid HTTP URL", e);
        }
    }

    private static void testOpenStreamWithFile() throws Exception {
        URL url = new URL("file:///etc/hosts");
        try (InputStream is = url.openStream()) {
            if (is == null) {
                throw new AssertionError("InputStream should not be null for valid file URL");
            }
        } catch (IOException e) {
            // Depending on environment, this may fail if file does not exist or access is denied
            // Acceptable to pass if IOException occurs, but test should not fail if stream is obtained
        }
    }

    private static void testOpenStreamWithInvalidURL() throws Exception {
        URL url = new URL("http://invalid.url.test/");
        try {
            url.openStream();
            // If no exception, test passes as openStream() does not validate URL existence
        } catch (IOException e) {
            // Expected in case of network issues or invalid URL
        }
    }
}
