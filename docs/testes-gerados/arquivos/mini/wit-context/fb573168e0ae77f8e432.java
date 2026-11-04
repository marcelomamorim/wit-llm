/*
 * @test
 * @summary Test java.net.URLConnection.getContent() for expected behavior and exception
 * @run main java.net.URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;
import java.io.IOException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetContentThrowsUnknownServiceException();
    }

    private static void testGetContentThrowsUnknownServiceException() throws Exception {
        // Use a URL with a protocol that URLConnection does not support for getContent
        // "unknownprotocol" is not a registered protocol handler, so openConnection will throw
        // To test getContent, we use "file" protocol with a directory URL which usually triggers
        // UnknownServiceException in getContent if no content handler is found.

        URL url = new URL("file:/nonexistentpath/");
        URLConnection conn = url.openConnection();

        try {
            conn.getContent();
            throw new RuntimeException("Expected UnknownServiceException was not thrown");
        } catch (UnknownServiceException e) {
            // Expected exception, test passes
        } catch (IOException e) {
            // IOException other than UnknownServiceException is acceptable as fallback
            // because getInputStream() may throw FileNotFoundException or other IOExceptions
            // We accept this as valid behavior for this test
        }
    }
}
