/*
 * @test
 * @summary Verifies URLConnection.getRequestProperty returns null when no request properties are set, returns values set via setRequestProperty and addRequestProperty, and returns null for unknown keys
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    static class TestConnection extends URLConnection {
        TestConnection(URL u) {
            super(u);
        }

        @Override
        public void connect() {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        TestConnection c = new TestConnection(url);

        check(c.getRequestProperty("Accept") == null,
              "expected null when no request properties were set");

        c.setRequestProperty("Accept", "text/plain");
        check("text/plain".equals(c.getRequestProperty("Accept")),
              "expected value set by setRequestProperty");

        check("text/plain".equals(c.getRequestProperty("accept")),
              "expected case-insensitive key lookup");

        check(c.getRequestProperty("X-Missing") == null,
              "expected null for unknown key");

        c.addRequestProperty("X-Multi", "a");
        c.addRequestProperty("X-Multi", "b");
        check("a".equals(c.getRequestProperty("X-Multi")),
              "expected first value for multi-valued key");
    }
}
