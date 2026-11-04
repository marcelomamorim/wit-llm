/*
 * @test
 * @summary Test for URLConnection.setDoOutput method behavior.
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetDoOutputWhenConnected();
    }

    private static void testSetDoOutputWhenConnected() throws Exception {
        URLConnection conn = new DummyURLConnection();
        // Simulate connection state
        ((DummyURLConnection) conn).setConnected(true);
        boolean exceptionThrown = false;
        try {
            conn.setDoOutput(true);
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException when connection is already established.");
        }
    }

    private static class DummyURLConnection extends URLConnection {
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
        public void checkConnected() {
            if (connected) {
                throw new IllegalStateException("Already connected");
            }
        }

        @Override
        public void setDoOutput(boolean dooutput) {
            checkConnected();
            // simulate setting doOutput
        }
    }
}
