/*
 * @test
 * @summary Verifies setAllowUserInteraction stores the value before connection and rejects changes after connect via IllegalStateException
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

        c.setAllowUserInteraction(true);
        if (!c.getAllowUserInteraction()) {
            throw new RuntimeException("allowUserInteraction should be true after setting true");
        }

        c.setAllowUserInteraction(false);
        if (c.getAllowUserInteraction()) {
            throw new RuntimeException("allowUserInteraction should be false after setting false");
        }

        c.setAllowUserInteraction(true);
        c.connect();

        try {
            c.setAllowUserInteraction(false);
            throw new RuntimeException("Expected IllegalStateException after connect");
        } catch (IllegalStateException expected) {
            // expected
        }

        if (!c.getAllowUserInteraction()) {
            throw new RuntimeException("value must not change when setter throws");
        }
    }
}
