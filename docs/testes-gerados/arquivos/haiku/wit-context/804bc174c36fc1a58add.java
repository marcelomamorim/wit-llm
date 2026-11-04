/*
 * @test
 * @summary Verify URLConnection.getRequestProperty returns request properties before connect and throws IllegalStateException after connect
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
        TestConnection conn = new TestConnection(url);

        if (conn.getRequestProperty("X-Missing") != null) {
            throw new RuntimeException("Expected null for missing request property before connect");
        }

        conn.setRequestProperty("X-Test", "value1");
        String value = conn.getRequestProperty("X-Test");
        if (!"value1".equals(value)) {
            throw new RuntimeException("Expected value1 but got " + value);
        }

        conn.connect();

        boolean thrown = false;
        try {
            conn.getRequestProperty("X-Test");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException after connect");
        }
    }
}
