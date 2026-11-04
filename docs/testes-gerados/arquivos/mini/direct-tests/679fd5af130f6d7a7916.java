/*
 * @test
 * @summary Test java.net.URLConnection.getRequestProperties() behavior
 * @run main java.net.URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class URLConnectionWitupTest {

    // Minimal subclass to instantiate abstract URLConnection
    static class DummyURLConnection extends URLConnection {
        DummyURLConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() {
            connected = true;
        }
    }

    public static void main(String[] args) throws Exception {
        // Create dummy URLConnection
        URL url = new URL("http://example.com");
        DummyURLConnection conn = new DummyURLConnection(url);

        // Initially not connected, getRequestProperties() should throw IllegalStateException
        try {
            conn.getRequestProperties();
            throw new RuntimeException("Expected IllegalStateException when not connected");
        } catch (IllegalStateException expected) {
            // expected
        }

        // Connect and test getRequestProperties() when requests == null
        conn.connect();
        // requests is protected and null by default, so getRequestProperties() returns empty map
        Map<String, List<String>> props = conn.getRequestProperties();
        if (props == null) {
            throw new RuntimeException("getRequestProperties() returned null instead of empty map");
        }
        if (!props.isEmpty()) {
            throw new RuntimeException("Expected empty map when requests is null");
        }

        // Since requests is protected and no public setter, we cannot set it directly.
        // So we cannot test the non-null requests.getHeaders(null) path without reflection,
        // which is disallowed by instructions.

        System.out.println("PASS");
    }
}
