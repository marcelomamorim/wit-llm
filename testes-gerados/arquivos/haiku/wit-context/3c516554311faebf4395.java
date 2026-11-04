/*
 * @test
 * @summary Verifies URLConnection.setUseCaches stores the value before connection and rejects changes after connect()
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

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        TestConnection c = new TestConnection(url);

        c.setUseCaches(false);
        check(!c.getUseCaches(), "useCaches should be false after setUseCaches(false)");

        c.setUseCaches(true);
        check(c.getUseCaches(), "useCaches should be true after setUseCaches(true)");

        c.connect();
        try {
            c.setUseCaches(false);
            throw new AssertionError("Expected IllegalStateException after connect()");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(c.getUseCaches(), "useCaches must remain unchanged after a rejected set");
    }
}
