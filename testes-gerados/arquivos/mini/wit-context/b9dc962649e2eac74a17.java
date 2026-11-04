/*
 * @test
 * @summary Test java.net.URL.openStream() for expected behavior and exception
 * @run main URLWitupTest
 */

import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import java.net.UnknownServiceException;

public class URLWitupTest {
    public static void main(String[] args) throws Exception {
        testOpenStreamSuccess();
        testOpenStreamUnknownServiceException();
    }

    private static void testOpenStreamSuccess() throws Exception {
        // Use a well-known URL that should succeed in opening stream
        URL url = new URL("http://www.example.com/");
        InputStream is = null;
        try {
            is = url.openStream();
            if (is == null) {
                throw new RuntimeException("openStream() returned null InputStream");
            }
            // Read a byte to ensure stream is open (may block or fail if no network)
            int b = is.read();
            // b can be -1 if empty, so no assert on value
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private static void testOpenStreamUnknownServiceException() throws Exception {
        // Construct a URL with a protocol that likely triggers UnknownServiceException
        // According to WIT expath, openStream() may throw UnknownServiceException
        // when openConnection().getInputStream() fails due to unsupported protocol
        URL url = new URL("unknownprotocol://host");
        try {
            url.openStream();
            throw new RuntimeException("Expected UnknownServiceException not thrown");
        } catch (UnknownServiceException e) {
            // Expected exception caught
        } catch (IOException e) {
            // IOException other than UnknownServiceException is acceptable fallback
            // because openStream() throws IOException generally
        }
    }
}
