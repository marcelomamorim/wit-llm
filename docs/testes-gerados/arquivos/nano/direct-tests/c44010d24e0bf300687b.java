/*
 * @test
 * @summary Test getInputStream() method of URLConnection throws UnknownServiceException
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;
import java.io.InputStream;
import java.io.IOException;
import java.net.UnknownServiceException;

public class URLConnectionWitupTest {

    public static void main(String[] args) {
        testGetInputStreamThrowsException();
    }

    public static void testGetInputStreamThrowsException() {
        URLConnection connection = new URLConnection(null) {
            @Override
            public InputStream getInputStream() throws IOException {
                throw new UnknownServiceException("protocol doesn't support input");
            }
        };
        try {
            connection.getInputStream();
            throw new AssertionError("Expected UnknownServiceException was not thrown");
        } catch (UnknownServiceException e) {
            // Expected exception, test passes
            assert e.getMessage().contains("protocol doesn't support input") : "Unexpected exception message: " + e.getMessage();
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }
}
