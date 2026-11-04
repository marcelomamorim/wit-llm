/*
 * @test
 * @summary Test HttpURLConnection.setAuthenticator throws UnsupportedOperationException
 * @run main HttpURLConnectionWitupTest
 */

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.Authenticator;

public class HttpURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy subclass to instantiate abstract HttpURLConnection
        class DummyHttpURLConnection extends HttpURLConnection {
            protected DummyHttpURLConnection(URL u) {
                super(u);
            }
            public void connect() {}
        }

        URL url = new URL("http://example.com");
        HttpURLConnection conn = new DummyHttpURLConnection(url);

        Authenticator auth = new Authenticator() {
            protected java.net.PasswordAuthentication getPasswordAuthentication() {
                return new java.net.PasswordAuthentication("user", "pass".toCharArray());
            }
        };

        try {
            conn.setAuthenticator(auth);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not supported")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
