/*
 * @test
 * @summary HttpURLConnection.setAuthenticator always throws UnsupportedOperationException in the base implementation
 * @run main HttpURLConnectionWitupTest
 */

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionWitupTest {

    static class TestConnection extends HttpURLConnection {
        TestConnection(URL u) {
            super(u);
        }

        @Override
        public void connect() {
        }

        @Override
        public void disconnect() {
        }

        @Override
        public boolean usingProxy() {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        TestConnection conn = new TestConnection(url);

        checkThrows(conn, null);
        checkThrows(conn, new Authenticator() {
        });
    }

    static void checkThrows(HttpURLConnection conn, Authenticator auth) {
        try {
            conn.setAuthenticator(auth);
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.startsWith("Supplying an authenticator")) {
                throw new RuntimeException("Unexpected message: " + msg);
            }
            return;
        }
        throw new RuntimeException("UnsupportedOperationException not thrown");
    }
}
