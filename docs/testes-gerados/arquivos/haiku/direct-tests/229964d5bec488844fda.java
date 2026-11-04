/*
 * @test
 * @summary URLConnection.setReadTimeout rejects negative values and stores non-negative ones
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
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        StubConnection c = new StubConnection(url);

        check(c.getReadTimeout() == 0, "default read timeout should be 0");

        c.setReadTimeout(0);
        check(c.getReadTimeout() == 0, "zero timeout should be stored");

        c.setReadTimeout(5000);
        check(c.getReadTimeout() == 5000, "positive timeout should be stored");

        c.setReadTimeout(Integer.MAX_VALUE);
        check(c.getReadTimeout() == Integer.MAX_VALUE, "MAX_VALUE timeout should be stored");

        try {
            c.setReadTimeout(-1);
            throw new RuntimeException("expected IllegalArgumentException for -1");
        } catch (IllegalArgumentException e) {
            check("timeout can not be negative".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(c.getReadTimeout() == Integer.MAX_VALUE,
              "timeout must be unchanged after rejected value");

        try {
            c.setReadTimeout(Integer.MIN_VALUE);
            throw new RuntimeException("expected IllegalArgumentException for MIN_VALUE");
        } catch (IllegalArgumentException e) {
            // expected
        }
        check(c.getReadTimeout() == Integer.MAX_VALUE,
              "timeout must be unchanged after MIN_VALUE rejection");
    }
}
