/*
 * @test
 * @summary Test java.net.URLConnection.setDoOutput(boolean) for IllegalStateException when connected
 * @run main java.net.URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URLConnection subclass to test setDoOutput
        class DummyURLConnection extends URLConnection {
            private boolean connected = false;
            protected DummyURLConnection(URL url) {
                super(url);
            }
            @Override
            public void connect() throws IOException {
                connected = true;
            }
            @Override
            public boolean getDoOutput() {
                return doOutput;
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

        // Initially not connected, setDoOutput should succeed
        conn.setDoOutput(true);
        if (!conn.getDoOutput()) {
            throw new RuntimeException("setDoOutput(true) did not set doOutput flag");
        }

        // Connect the connection
        conn.connect();

        // After connected, setDoOutput should throw IllegalStateException
        boolean threw = false;
        try {
            conn.setDoOutput(false);
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("setDoOutput did not throw IllegalStateException when connected");
        }
    }
}
