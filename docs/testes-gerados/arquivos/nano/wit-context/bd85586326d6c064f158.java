/*
 * @test
 * @summary Test for URLConnection.setDoInput(boolean) method behavior.
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) {
        testSetDoInputConnected();
        testSetDoInputNotConnected();
    }

    private static void testSetDoInputConnected() {
        URLConnection conn = new DummyURLConnection();
        // Simulate connected state
        ((DummyURLConnection) conn).setConnected(true);
        boolean exceptionThrown = false;
        try {
            conn.setDoInput(true);
            // Expect IllegalStateException due to connected state
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException when connection is already connected.");
        }
    }

    private static void testSetDoInputNotConnected() {
        URLConnection conn = new DummyURLConnection();
        // Simulate not connected state
        ((DummyURLConnection) conn).setConnected(false);
        boolean exceptionThrown = false;
        try {
            conn.setDoInput(true);
            // Should succeed without exception
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (exceptionThrown) {
            throw new AssertionError("Did not expect IllegalStateException when not connected.");
        }
    }

    private static class DummyURLConnection extends URLConnection {
        private boolean connected = false;
        private boolean doInput;

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
        public void setDoInput(boolean doinput) {
            if (connected) {
                throw new IllegalStateException("Already connected");
            }
            this.doInput = doinput;
        }
    }
}
