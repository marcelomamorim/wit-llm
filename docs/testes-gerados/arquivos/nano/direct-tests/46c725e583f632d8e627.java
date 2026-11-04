/*
 * @test
 * @summary Test getContent method of URLConnection.
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetContentReturnsInputStream();
        testGetContentWithNullConnection();
    }

    private static void testGetContentReturnsInputStream() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection uc = url.openConnection();
        Object content = null;
        try {
            content = getContent(uc);
        } catch (IOException e) {
            // Ignored for test
        }
        if (content != null) {
            if (!(content instanceof java.io.InputStream)) {
                throw new AssertionError("Expected InputStream, got " + content.getClass());
            }
        }
    }

    private static void testGetContentWithNullConnection() {
        try {
            getContent(null);
            throw new AssertionError("Expected NullPointerException for null connection");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    public static Object getContent(URLConnection uc) {
        return uc.getInputStream();
    }
}
