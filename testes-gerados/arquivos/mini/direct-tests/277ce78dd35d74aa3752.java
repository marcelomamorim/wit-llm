/*
 * @test
 * @summary Test java.net.URLConnection.setConnectTimeout behavior
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URLConnection subclass to test setConnectTimeout
        class DummyURLConnection extends URLConnection {
            protected DummyURLConnection(URL url) {
                super(url);
            }
            public void connect() {}
        }

        URL url = new URL("http://example.com");
        DummyURLConnection conn = new DummyURLConnection(url);

        // Test setting a valid positive timeout
        conn.setConnectTimeout(1000);
        if (conn.getConnectTimeout() != 1000) {
            throw new RuntimeException("setConnectTimeout failed to set positive value");
        }

        // Test setting zero timeout
        conn.setConnectTimeout(0);
        if (conn.getConnectTimeout() != 0) {
            throw new RuntimeException("setConnectTimeout failed to set zero value");
        }

        // Test setting negative timeout throws IllegalArgumentException
        boolean threw = false;
        try {
            conn.setConnectTimeout(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("timeout can not be negative")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("setConnectTimeout did not throw IllegalArgumentException for negative timeout");
        }
    }
}
