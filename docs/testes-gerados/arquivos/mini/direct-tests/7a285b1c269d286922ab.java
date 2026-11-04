/*
 * @test
 * @summary Test URLConnection.getOutputStream() throws UnknownServiceException
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;
import java.io.OutputStream;
import java.io.IOException;
import java.net.UnknownServiceException;
import java.net.URL;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URLConnection subclass to test getOutputStream
        URL url = new URL("http://example.com");
        URLConnection conn = new URLConnection(url) {
            @Override
            public void connect() {}
        };

        try {
            OutputStream os = conn.getOutputStream();
            throw new RuntimeException("Expected UnknownServiceException not thrown");
        } catch (UnknownServiceException use) {
            // Expected exception
            String msg = use.getMessage();
            if (!"protocol doesn't support output".equals(msg)) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
