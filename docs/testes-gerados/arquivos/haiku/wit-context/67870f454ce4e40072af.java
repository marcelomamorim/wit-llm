/*
 * @test
 * @summary Verifies that HttpURLConnection.setAuthenticator rejects calls with UnsupportedOperationException and a descriptive message
 * @run main HttpURLConnectionWitupTest
 */

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionWitupTest {

    static final class Conn extends HttpURLConnection {
        Conn(URL u) {
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
        Conn c = new Conn(new URL("http://localhost/"));
        try {
            c.setAuthenticator(null);
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null) {
                throw new AssertionError("Message must not be null");
            }
            if (!msg.startsWith("Supplying an authenticator is not supported by ")) {
                throw new AssertionError("Unexpected message: " + msg);
            }
            if (!msg.contains(Conn.class.getName())) {
                throw new AssertionError("Message does not mention runtime class: " + msg);
            }
        }
    }
}
