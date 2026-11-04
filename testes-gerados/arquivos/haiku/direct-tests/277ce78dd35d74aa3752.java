/*
 * @test
 * @summary Verifies argument validation of URLConnection.setConnectTimeout
 * @run main URLConnectionWitupTest
 */

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    static class TestConnection extends URLConnection {
        TestConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() throws IOException {
        }
    }

    static void expectIllegalArgument(URLConnection conn, int timeout) {
        try {
            conn.setConnectTimeout(timeout);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException not thrown for timeout " + timeout);
    }

    static void expectAccepted(URLConnection conn, int timeout) {
        try {
            conn.setConnectTimeout(timeout);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unexpected IllegalArgumentException for timeout " + timeout, e);
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        URLConnection conn = new TestConnection(url);

        expectIllegalArgument(conn, -1);
        expectIllegalArgument(conn, -100);
        expectIllegalArgument(conn, Integer.MIN_VALUE);

        expectAccepted(conn, 0);
        expectAccepted(conn, 1);
        expectAccepted(conn, 5000);
        expectAccepted(conn, Integer.MAX_VALUE);

        expectAccepted(conn, 0);
        expectIllegalArgument(conn, -1);
    }
}
