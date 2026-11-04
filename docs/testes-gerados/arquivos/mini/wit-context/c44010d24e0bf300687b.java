/*
 * @test
 * @summary Test URLConnection.getInputStream() throws UnknownServiceException
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import java.io.IOException;
import java.net.UnknownServiceException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URLConnection subclass to invoke getInputStream()
        URL dummyURL = new URL("http://example.com");
        URLConnection conn = new URLConnection(dummyURL) {
            @Override
            public void connect() throws IOException {
                // no-op
            }
        };

        try {
            InputStream is = conn.getInputStream();
            throw new RuntimeException("Expected UnknownServiceException not thrown");
        } catch (UnknownServiceException use) {
            // Expected exception
        }
    }
}
