/*
 * @test
 * @summary Verifies URLConnection.setReadTimeout rejects negative timeouts and preserves the previous value on failure
 * @run main URLConnectionWitupTest
 */
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    private static final class TestConnection extends URLConnection {
        TestConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() {
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        TestConnection conn = new TestConnection(url);

        if (conn.getReadTimeout() != 0) {
            throw new RuntimeException("Default read timeout expected to be 0");
        }

        conn.setReadTimeout(0);
        if (conn.getReadTimeout() != 0) {
            throw new RuntimeException("Zero timeout should be accepted");
        }

        conn.setReadTimeout(5000);
        if (conn.getReadTimeout() != 5000) {
            throw new RuntimeException("Positive timeout should be stored");
        }

        expectIllegalArgument(conn, -1);
        if (conn.getReadTimeout() != 5000) {
            throw new RuntimeException("Value changed after rejected negative timeout");
        }

        expectIllegalArgument(conn, Integer.MIN_VALUE);
        if (conn.getReadTimeout() != 5000) {
            throw new RuntimeException("Value changed after rejected MIN_VALUE timeout");
        }
    }

    private static void expectIllegalArgument(URLConnection conn, int timeout) {
        try {
            conn.setReadTimeout(timeout);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for timeout " + timeout);
    }
}
