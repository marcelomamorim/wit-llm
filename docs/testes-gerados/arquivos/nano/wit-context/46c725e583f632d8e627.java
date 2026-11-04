/*
 * @test
 * @summary Test for java.net.URLConnection.getContent(URLConnection)
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetContentReturnsInputStream();
        testGetContentThrowsUnknownServiceException();
    }

    private static void testGetContentReturnsInputStream() throws IOException {
        URLConnection mockConnection = new URLConnection(null) {
            @Override
            public java.io.InputStream getInputStream() {
                return new java.io.ByteArrayInputStream(new byte[0]);
            }
        };
        Object result = URLConnection.getContent(mockConnection);
        if (!(result instanceof java.io.InputStream)) {
            throw new AssertionError("Expected InputStream, got " + result.getClass());
        }
    }

    private static void testGetContentThrowsUnknownServiceException() {
        URLConnection mockConnection = new URLConnection(null) {
            @Override
            public java.io.InputStream getInputStream() {
                throw new java.net.UnknownServiceException("Unknown service");
            }
        };
        try {
            URLConnection.getContent(mockConnection);
            throw new AssertionError("Expected UnknownServiceException to be thrown");
        } catch (java.net.UnknownServiceException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
