/*
 * @test
 * @summary Test URLConnection.getOutputStream throws UnknownServiceException
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;
import java.io.IOException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URL to instantiate URLConnection subclass anonymously
        URL url = new URL("http://example.com");
        URLConnection conn = new URLConnection(url) {
            @Override
            public void connect() throws IOException {
                // no-op
            }
        };

        try {
            conn.getOutputStream();
            throw new RuntimeException("Expected UnknownServiceException not thrown");
        } catch (UnknownServiceException e) {
            // Expected exception
        }
    }
}
