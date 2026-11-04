/*
 * @test
 * @summary Test java.net.URLConnection.setReadTimeout behavior
 * @run main URLConnectionWitupTest
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

        // Test setting a valid positive timeout
        conn.setReadTimeout(1000);
        if (conn.getReadTimeout() != 1000) {
            throw new RuntimeException("setReadTimeout failed to set 1000");
        }

        // Test setting zero timeout
        conn.setReadTimeout(0);
        if (conn.getReadTimeout() != 0) {
            throw new RuntimeException("setReadTimeout failed to set 0");
        }

        // Test setting negative timeout throws IllegalArgumentException
        boolean threw = false;
        try {
            conn.setReadTimeout(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("setReadTimeout did not throw IllegalArgumentException for negative value");
        }
    }
}
