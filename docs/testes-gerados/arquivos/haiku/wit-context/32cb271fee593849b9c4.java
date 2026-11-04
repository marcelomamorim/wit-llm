/*
 * @test
 * @summary Verifies URLConnection.setIfModifiedSince stores the value while not connected and throws IllegalStateException once connected, leaving the stored value unchanged
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    static class TestConnection extends URLConnection {
        TestConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() {
            connected = true;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        TestConnection conn = new TestConnection(url);

        conn.setIfModifiedSince(5L);
        if (conn.getIfModifiedSince() != 5L) {
            throw new RuntimeException("ifModifiedSince not stored before connect: " + conn.getIfModifiedSince());
        }

        conn.connect();

        boolean thrown = false;
        try {
            conn.setIfModifiedSince(10L);
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IllegalStateException not thrown when setting ifModifiedSince after connect");
        }

        if (conn.getIfModifiedSince() != 5L) {
            throw new RuntimeException("ifModifiedSince changed despite exception: " + conn.getIfModifiedSince());
        }
    }
}
