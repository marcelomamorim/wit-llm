/*
 * @test
 * @summary Test java.net.URLConnection.getContent(Class<?>[] classes) for expected behavior and exception
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
        // Use a URL with a protocol that does not have a content handler to provoke UnknownServiceException
        URL url = new URL("http://localhost:0/");
        URLConnection conn = url.openConnection();

        // Connect is not mandatory before getContent, but getInputStream is called inside getContent
        // The test expects UnknownServiceException to be thrown by getContent if no content handler is found

        try {
            Class<?>[] classes = new Class<?>[] { String.class };
            conn.getContent(classes);
            throw new RuntimeException("Expected UnknownServiceException was not thrown");
        } catch (UnknownServiceException use) {
            // Expected exception
        } catch (IOException ioe) {
            // IOException is allowed as getInputStream may throw it
            // But UnknownServiceException is a subclass of IOException, so catch separately
            throw ioe;
        }
    }
}
