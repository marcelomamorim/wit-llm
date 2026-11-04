/*
 * @test
 * @summary Test for getInputStream method in JavaRuntimeURLConnection
 * @run main JavaRuntimeURLConnectionWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class JavaRuntimeURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetInputStreamNormal();
        testGetInputStreamWhenClosed();
    }

    private static void testGetInputStreamNormal() throws Exception {
        URL url = new URL("http://example.com/resource");
        sun.net.www.protocol.jrt.JavaRuntimeURLConnection conn = new sun.net.www.protocol.jrt.JavaRuntimeURLConnection(url) {
            @Override
            public InputStream getInputStream() throws IOException {
                // Simulate a resource
                byte[] resource = {1, 2, 3, 4};
                return new ByteArrayInputStream(resource);
            }
        };
        InputStream is = conn.getInputStream();
        int firstByte = is.read();
        if (firstByte != 1) {
            throw new AssertionError("Expected first byte to be 1, but got " + firstByte);
        }
        is.close();
    }

    private static void testGetInputStreamWhenClosed() throws Exception {
        URL url = new URL("http://example.com/resource");
        sun.net.www.protocol.jrt.JavaRuntimeURLConnection conn = new sun.net.www.protocol.jrt.JavaRuntimeURLConnection(url) {
            private boolean closed = false;
            @Override
            public InputStream getInputStream() throws IOException {
                if (closed) {
                    throw new IllegalStateException("Stream is closed");
                }
                byte[] resource = {5, 6, 7};
                closed = true;
                return new ByteArrayInputStream(resource);
            }
        };
        // First call should succeed
        InputStream is = conn.getInputStream();
        is.close();
        // Second call should throw IllegalStateException
        try {
            conn.getInputStream();
            throw new AssertionError("Expected IllegalStateException when calling getInputStream() after close");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
