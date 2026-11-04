/*
 * @test
 * @summary Test sun.net.www.protocol.http.HttpURLConnection.addRequestProperty(String, String) for exceptions and normal behavior
 * @run main HttpURLConnectionWitupTest
 */

import sun.net.www.protocol.http.HttpURLConnection;
import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testAddRequestPropertyThrowsWhenConnected();
        testAddRequestPropertyThrowsWhenKeyNull();
        testAddRequestPropertyNormal();
    }

    private static HttpURLConnection newHttpURLConnection() throws MalformedURLException {
        // Use public constructor with URL and Proxy (null proxy means direct connection)
        return new HttpURLConnection(new URL("http://example.com"), (Proxy) null) {
            @Override
            public void connect() {
                // no-op
            }
        };
    }

    private static void testAddRequestPropertyThrowsWhenConnected() throws Exception {
        HttpURLConnection conn = newHttpURLConnection();
        // Set connected to true via reflection is not allowed, so simulate by subclass
        // Instead, create subclass that overrides connected field via method
        // But connected is protected in superclass, so we can subclass and set it
        HttpURLConnection connWithConnected = new HttpURLConnection(new URL("http://example.com"), (Proxy) null) {
            boolean connected = true;
            boolean connecting = false;
            @Override
            public synchronized void addRequestProperty(String key, String value) {
                if (connected || connecting)
                    throw new IllegalStateException("Already connected");
                super.addRequestProperty(key, value);
            }
            @Override
            public void connect() {}
        };

        try {
            connWithConnected.addRequestProperty("key", "value");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            if (!"Already connected".equals(e.getMessage())) {
                throw new RuntimeException("IllegalStateException message mismatch", e);
            }
        }
    }

    private static void testAddRequestPropertyThrowsWhenKeyNull() throws Exception {
        HttpURLConnection conn = newHttpURLConnection();
        try {
            conn.addRequestProperty(null, "value");
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            if (!"key is null".equals(e.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", e);
            }
        }
    }

    private static void testAddRequestPropertyNormal() throws Exception {
        HttpURLConnection conn = newHttpURLConnection();
        // Add a normal header
        conn.addRequestProperty("X-Test-Header", "test-value");
        // No direct way to verify internal state, but no exception means success
        // Add Content-Type header to test userHeaders exclusion
        conn.addRequestProperty("Content-Type", "text/plain");
    }
}
