/*
 * @test
 * @summary Verifies that URLConnection.setAllowUserInteraction stores the flag observable via getAllowUserInteraction on an unconnected connection
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

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        TestConnection c = new TestConnection(url);

        c.setAllowUserInteraction(true);
        check(c.getAllowUserInteraction(), "expected true after setting true");

        c.setAllowUserInteraction(false);
        check(!c.getAllowUserInteraction(), "expected false after setting false");

        c.setAllowUserInteraction(true);
        c.setAllowUserInteraction(true);
        check(c.getAllowUserInteraction(), "expected true after repeated set");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
