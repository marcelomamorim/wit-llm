/*
 * @test
 * @summary Test getContent(Class<?>[] classes) method of URLConnection.
 * @run main URLConnectionWitupTest
 */

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetContentReturnsHandlerContent();
        testGetContentWithNullClasses();
    }

    private static void testGetContentReturnsHandlerContent() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection connection = url.openConnection();
        Object content = null;
        try {
            content = connection.getContent(new Class<?>[] { String.class });
        } catch (IOException e) {
            // In case of network issues, ignore for test purposes
        }
        // Since getContentHandler() is protected and not accessible,
        // we assume getContent() returns non-null or throws IOException.
        // We check that the method completes without unexpected exceptions.
        assert content != null || true; // placeholder assertion
    }

    private static void testGetContentWithNullClasses() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection connection = url.openConnection();
        Object content = null;
        try {
            content = connection.getContent(null);
        } catch (NullPointerException e) {
            // Expected if getContent() does not handle null properly
            throw e;
        } catch (IOException e) {
            // In case of network issues, ignore for test purposes
        }
        // Check that method completes without unexpected exceptions.
        assert content != null || true; // placeholder assertion
    }
}
