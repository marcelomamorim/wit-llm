/*
 * @test
 * @summary Test java.net.URLConnection.getRequestProperty(String) behavior
 * @run main java.net.URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {

    // Minimal subclass to allow instantiation of abstract URLConnection
    static class DummyURLConnection extends URLConnection {
        DummyURLConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() throws IOException {
            connected = true;
        }
    }

    public static void main(String[] args) throws Exception {
        testGetRequestPropertyBeforeConnect();
        testGetRequestPropertyAfterConnectNoRequests();
        testGetRequestPropertyAfterConnectWithRequest();
    }

    private static void testGetRequestPropertyBeforeConnect() {
        try {
            URL url = new URL("http://example.com");
            DummyURLConnection conn = new DummyURLConnection(url);
            // Not connected yet, should throw IllegalStateException
            conn.getRequestProperty("key");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    private static void testGetRequestPropertyAfterConnectNoRequests() throws Exception {
        URL url = new URL("http://example.com");
        DummyURLConnection conn = new DummyURLConnection(url);
        conn.connect();
        // requests is null by default, so getRequestProperty should return null
        String value = conn.getRequestProperty("anyKey");
        if (value != null) {
            throw new RuntimeException("Expected null but got: " + value);
        }
    }

    private static void testGetRequestPropertyAfterConnectWithRequest() throws Exception {
        URL url = new URL("http://example.com");
        DummyURLConnection conn = new DummyURLConnection(url);
        conn.connect();

        // Use setRequestProperty to add a request property
        conn.setRequestProperty("TestKey", "TestValue");

        String val = conn.getRequestProperty("TestKey");
        if (!"TestValue".equals(val)) {
            throw new RuntimeException("Expected 'TestValue' but got: " + val);
        }

        // Query a key that does not exist
        String missing = conn.getRequestProperty("MissingKey");
        if (missing != null) {
            throw new RuntimeException("Expected null for missing key but got: " + missing);
        }
    }
}
