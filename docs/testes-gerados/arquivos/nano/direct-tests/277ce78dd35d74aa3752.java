/*
 * @test
 * @summary Test for URLConnection.setConnectTimeout(int)
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) {
        testSetConnectTimeoutValid();
        testSetConnectTimeoutNegative();
    }

    static void testSetConnectTimeoutValid() {
        URLConnection conn = new DummyURLConnection();
        int timeout = 5000;
        conn.setConnectTimeout(timeout);
        assert ((DummyURLConnection) conn).getConnectTimeout() == timeout : "Timeout not set correctly";
    }

    static void testSetConnectTimeoutNegative() {
        URLConnection conn = new DummyURLConnection();
        try {
            conn.setConnectTimeout(-1);
            assert false : "Expected IllegalArgumentException for negative timeout";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("timeout can not be negative") : "Unexpected exception message";
        }
    }

    static class DummyURLConnection extends URLConnection {
        private int connectTimeout = 0;
        protected DummyURLConnection() {
            super(null);
        }
        @Override
        public void connect() throws IOException {
            // no-op
        }
        public int getConnectTimeout() {
            return connectTimeout;
        }
    }
}
