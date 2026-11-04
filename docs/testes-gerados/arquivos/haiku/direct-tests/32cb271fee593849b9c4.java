/*
 * @test
 * @summary Verify URLConnection.setIfModifiedSince stores the value before connection and rejects changes after connection
 * @run main URLConnectionWitupTest
 */

import java.io.IOException;
import java.net.URL;

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
        if (c.getIfModifiedSince() != 0L) {
            throw new RuntimeException("Default ifModifiedSince should be 0");
        }

        c.setIfModifiedSince(12345L);
        if (c.getIfModifiedSince() != 12345L) {
            throw new RuntimeException("ifModifiedSince not stored before connect");
        }

        c.setIfModifiedSince(Long.MAX_VALUE);
        if (c.getIfModifiedSince() != Long.MAX_VALUE) {
            throw new RuntimeException("Long.MAX_VALUE not stored before connect");
        }

        TestConnection d = new TestConnection(url);
        d.setIfModifiedSince(777L);
        d.connect();

        boolean thrown = false;
        try {
            d.setIfModifiedSince(999L);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException after connect");
        }
        if (d.getIfModifiedSince() != 777L) {
            throw new RuntimeException("ifModifiedSince changed after failed set");
        }
    }
}
