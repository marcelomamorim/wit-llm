/*
 * @test
 * @summary Test java.net.URLConnection.getRequestProperties() behavior and exception
 * @run main URLConnectionWitupTest
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
        // Test 1: getRequestProperties() before connect() returns empty map if requests is null
        URL url = new URL("http://example.com");
        DummyURLConnection conn = new DummyURLConnection(url);

        // requests field is null by default, so getRequestProperties should return empty map
        Map<String, List<String>> props = conn.getRequestProperties();
        if (props == null) {
            throw new AssertionError("getRequestProperties() returned null instead of empty map");
        }
        if (!props.isEmpty()) {
            throw new AssertionError("getRequestProperties() returned non-empty map when requests is null");
        }

        // Test 2: getRequestProperties() after connect() throws IllegalStateException
        conn.connect();
        try {
            conn.getRequestProperties();
            throw new AssertionError("Expected IllegalStateException after connect() but none thrown");
        } catch (IllegalStateException expected) {
            // expected
        }

        System.out.println("Test passed");
    }
}
