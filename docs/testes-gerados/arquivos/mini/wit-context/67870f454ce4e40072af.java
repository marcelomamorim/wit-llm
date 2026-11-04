/*
 * @test
 * @summary Test HttpURLConnection.setAuthenticator throws UnsupportedOperationException
 * @run main HttpURLConnectionWitupTest
 */

import java.net.HttpURLConnection;
import java.net.Authenticator;
import java.net.URL;

public class HttpURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal subclass to instantiate abstract HttpURLConnection
        class DummyHttpURLConnection extends HttpURLConnection {
            protected DummyHttpURLConnection(URL u) {
                super(u);
            }
            public void connect() {
                // no-op
            }
        }

        URL url = new URL("http://example.com");
        HttpURLConnection conn = new DummyHttpURLConnection(url);

        // Test that setAuthenticator throws UnsupportedOperationException
        try {
            conn.setAuthenticator(null);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not supported")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
