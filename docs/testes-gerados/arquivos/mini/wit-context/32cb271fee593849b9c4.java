/*
 * @test
 * @summary Test java.net.URLConnection.setIfModifiedSince(long) for IllegalStateException when connected
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URLConnection subclass to test setIfModifiedSince
        class DummyURLConnection extends URLConnection {
            private boolean connected = false;
            protected DummyURLConnection(URL url) {
                super(url);
            }
            @Override
            public void connect() throws IOException {
                connected = true;
                this.connected = true;
            }
            @Override
            public boolean getDoInput() {
                return true;
            }
            @Override
            public void setIfModifiedSince(long ifmodifiedsince) {
                // call super method which calls checkConnected
                super.setIfModifiedSince(ifmodifiedsince);
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

        // Initially not connected, setIfModifiedSince should succeed
        conn.setIfModifiedSince(123456789L);

        // Simulate connection
        conn.connect();

        // After connected, setIfModifiedSince should throw IllegalStateException
        boolean threw = false;
        try {
            conn.setIfModifiedSince(987654321L);
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when calling setIfModifiedSince after connected");
        }

        System.out.println("URLConnectionWitupTest passed");
    }
}
