/*
 * @test
 * @summary Test java.net.URLConnection.setConnectTimeout(int) for valid and invalid inputs
 * @run main java.net.URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URLConnection subclass instance for testing
        URL url = new URL("http://example.com");
        URLConnection conn = new URLConnection(url) {
            @Override
            public void connect() {
                // no-op
            }
        };

        // Test valid timeout values
        conn.setConnectTimeout(0);
        conn.setConnectTimeout(1000);

        // Test negative timeout triggers IllegalArgumentException
        boolean threw = false;
        try {
            conn.setConnectTimeout(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
            if (!"timeout can not be negative".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        }

        System.out.println("URLConnection.setConnectTimeout(int) tests passed");
    }
}
