/*
 * @test
 * @summary Test java.net.URLConnection.setReadTimeout(int) for valid and invalid inputs
 * @run main java.net.URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URLConnection subclass to test setReadTimeout
        class DummyURLConnection extends URLConnection {
            protected DummyURLConnection(URL url) {
                super(url);
            }
            public void connect() {}
        }

        URL url = new URL("http://example.com");
        DummyURLConnection conn = new DummyURLConnection(url);

        // Test valid timeout values
        conn.setReadTimeout(0); // zero timeout allowed
        conn.setReadTimeout(1000); // positive timeout

        // Test negative timeout triggers IllegalArgumentException
        boolean threw = false;
        try {
            conn.setReadTimeout(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("timeout")) {
                throw new RuntimeException("Exception message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        }

        System.out.println("URLConnection.setReadTimeout test passed");
    }
}
