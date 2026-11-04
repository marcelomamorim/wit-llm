/*
 * @test
 * @summary Test URLConnection.getInputStream() throws UnknownServiceException
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;
import java.io.InputStream;
import java.io.IOException;
import java.net.UnknownServiceException;
import java.net.URL;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URLConnection subclass to test getInputStream()
        URL url = new URL("http://example.com");
        URLConnection conn = new URLConnection(url) {
            @Override
            public void connect() {}
        };

        try {
            conn.getInputStream();
            throw new RuntimeException("Expected UnknownServiceException not thrown");
        } catch (UnknownServiceException e) {
            // Expected exception
            String msg = e.getMessage();
            if (!"protocol doesn't support input".equals(msg)) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
