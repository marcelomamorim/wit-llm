/*
 * @test
 * @summary Test for URLConnection.setUseCaches method behavior.
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetUseCachesWhenConnected();
        testSetUseCachesWhenNotConnected();
    }

    private static void testSetUseCachesWhenConnected() throws Exception {
        URLConnection conn = new DummyURLConnection();
        // Simulate connection established
        ((DummyURLConnection) conn).setConnected(true);
        boolean exceptionThrown = false;
        try {
            conn.setUseCaches(true);
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException when connection is established.");
        }
    }

    private static void testSetUseCachesWhenNotConnected() throws Exception {
        URLConnection conn = new DummyURLConnection();
        // Connection not established
        boolean exceptionThrown = false;
        try {
            conn.setUseCaches(false);
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (exceptionThrown) {
            throw new AssertionError("Did not expect IllegalStateException when not connected.");
        }
        if (((DummyURLConnection) conn).useCaches != false) {
            throw new AssertionError("useCaches should be set to false.");
        }
    }

    static class DummyURLConnection extends URLConnection {
        private boolean connected = false;
        public boolean useCaches = false;
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
        public void setUseCaches(boolean usecaches) {
            checkConnected();
            this.useCaches = usecaches;
        }
        public void checkConnected() {
            if (connected) {
                throw new IllegalStateException("Already connected");
            }
        }
    }
}
