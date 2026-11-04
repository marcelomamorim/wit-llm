/*
 * @test
 * @summary Verifies setDoOutput stores the flag when unconnected and throws IllegalStateException once connected
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
            connected = true;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");

        TestConnection c = new TestConnection(url);
        c.setDoOutput(true);
        if (!c.getDoOutput()) {
            throw new AssertionError("doOutput should be true after setDoOutput(true)");
        }
        c.setDoOutput(false);
        if (c.getDoOutput()) {
            throw new AssertionError("doOutput should be false after setDoOutput(false)");
        }

        TestConnection connectedConn = new TestConnection(url);
        connectedConn.connect();
        try {
            connectedConn.setDoOutput(true);
            throw new AssertionError("Expected IllegalStateException when connected");
        } catch (IllegalStateException expected) {
        }
    }
}
