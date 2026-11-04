/*
 * @test
 * @summary URLConnection.setDoInput stores the flag before connection and rejects changes after connection
 * @run main URLConnectionWitupTest
 */

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    static class StubConnection extends URLConnection {
        StubConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() throws IOException {
            connected = true;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        StubConnection c = new StubConnection(url);

        check(c.getDoInput(), "default doInput should be true");

        c.setDoInput(false);
        check(!c.getDoInput(), "doInput should be false after setDoInput(false)");

        c.setDoInput(true);
        check(c.getDoInput(), "doInput should be true after setDoInput(true)");

        c.connected = true;
        boolean thrown = false;
        try {
            c.setDoInput(false);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "setDoInput after connection should throw IllegalStateException");
        check(c.getDoInput(), "doInput should be unchanged after rejected setDoInput");
    }
}
