/*
 * @test
 * @summary Test java.net.URLConnection.setDoInput(boolean) for IllegalStateException when connected
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URLConnection subclass to test setDoInput
        class DummyURLConnection extends URLConnection {
            private boolean connected = false;
            protected DummyURLConnection(URL url) {
                super(url);
            }
            @Override
            public void connect() {
                connected = true;
            }
            @Override
            public boolean getDoInput() {
                return doInput;
            }
            @Override
            protected void checkConnected() {
                if (connected) {
                    throw new IllegalStateException("Already connected");
                }
            }
        }

        URL url = new URL("http://example.com");
        DummyURLConnection conn = new DummyURLConnection(url);

        // Initially not connected, setDoInput should succeed
        conn.setDoInput(true);
        if (!conn.getDoInput()) {
            throw new AssertionError("doInput should be true after setDoInput(true)");
        }

        // Connect the connection
        conn.connect();

        // After connected, setDoInput should throw IllegalStateException
        boolean threw = false;
        try {
            conn.setDoInput(false);
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when calling setDoInput after connected");
        }

        System.out.println("Test passed.");
    }
}
