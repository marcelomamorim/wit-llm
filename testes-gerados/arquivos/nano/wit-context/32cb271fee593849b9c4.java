/*
 * @test
 * @summary Test for URLConnection.setIfModifiedSince method behavior.
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) {
        testSetIfModifiedSinceConnected();
        testSetIfModifiedSinceNotConnected();
    }

    private static void testSetIfModifiedSinceConnected() {
        URLConnection conn = new DummyURLConnection();
        // Simulate connection established
        ((DummyURLConnection) conn).setConnected(true);
        try {
            conn.setIfModifiedSince(123456789L);
            System.out.println("testSetIfModifiedSinceConnected: PASSED");
        } catch (IllegalStateException e) {
            System.out.println("testSetIfModifiedSinceConnected: FAILED - Unexpected exception");
        } catch (Exception e) {
            System.out.println("testSetIfModifiedSinceConnected: FAILED - " + e);
        }
    }

    private static void testSetIfModifiedSinceNotConnected() {
        URLConnection conn = new DummyURLConnection();
        // Simulate not connected
        ((DummyURLConnection) conn).setConnected(false);
        try {
            conn.setIfModifiedSince(987654321L);
            System.out.println("testSetIfModifiedSinceNotConnected: FAILED - No exception thrown");
        } catch (IllegalStateException e) {
            System.out.println("testSetIfModifiedSinceNotConnected: PASSED");
        } catch (Exception e) {
            System.out.println("testSetIfModifiedSinceNotConnected: FAILED - " + e);
        }
    }

    static class DummyURLConnection extends URLConnection {
        private boolean connected = false;
        protected DummyURLConnection() {
            super(null);
        }
        public void setConnected(boolean connected) {
            this.connected = connected;
        }
        @Override
        public void connect() throws IOException {
            // no-op
        }
        @Override
        public void setIfModifiedSince(long ifmodifiedsince) {
            if (connected) {
                throw new IllegalStateException("Already connected");
            }
            // simulate setting the field
        }
    }
}
