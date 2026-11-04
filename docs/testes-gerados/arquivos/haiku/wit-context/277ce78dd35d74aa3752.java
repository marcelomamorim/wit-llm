/*
 * @test
 * @summary Verify URLConnection.setConnectTimeout rejects negative timeouts and stores non-negative values
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {

    static final class Conn extends URLConnection {
        Conn(URL url) {
            super(url);
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
        Conn c = new Conn(url);

        c.setConnectTimeout(0);
        check(c.getConnectTimeout() == 0, "zero timeout not stored");

        c.setConnectTimeout(1000);
        check(c.getConnectTimeout() == 1000, "positive timeout not stored");

        c.setConnectTimeout(Integer.MAX_VALUE);
        check(c.getConnectTimeout() == Integer.MAX_VALUE, "max timeout not stored");

        c.setConnectTimeout(1000);

        int[] invalid = {-1, Integer.MIN_VALUE};
        for (int value : invalid) {
            boolean thrown = false;
            try {
                c.setConnectTimeout(value);
            } catch (IllegalArgumentException e) {
                thrown = true;
                check("timeout can not be negative".equals(e.getMessage()),
                      "unexpected message for " + value + ": " + e.getMessage());
            }
            check(thrown, "negative timeout accepted: " + value);
            check(c.getConnectTimeout() == 1000,
                  "timeout changed after rejected value " + value);
        }
    }
}
