/*
 * @test
 * @summary Test HttpURLConnection.setFixedLengthStreamingMode(long) for correct exception throwing and state setting
 * @run main HttpURLConnectionWitupTest
 */

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionWitupTest {

    // Subclass to allow instantiation of abstract HttpURLConnection
    static class MyHttpURLConnection extends HttpURLConnection {
        protected MyHttpURLConnection(URL u) {
            super(u);
        }
        public void connect() {
            connected = true;
        }
        public void disconnect() {}
        public boolean usingProxy() { return false; }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://example.com");

        // Test 1: IllegalStateException if connected == true
        MyHttpURLConnection conn1 = new MyHttpURLConnection(url);
        conn1.connect(); // sets connected = true
        try {
            conn1.setFixedLengthStreamingMode(10L);
            throw new RuntimeException("Expected IllegalStateException for connected == true");
        } catch (IllegalStateException e) {
            // expected
        }

        // Test 2: IllegalStateException if chunkLength != -1 and connected == false
        MyHttpURLConnection conn2 = new MyHttpURLConnection(url);
        conn2.chunkLength = 1; // chunkLength != -1
        // connected is false by default
        try {
            conn2.setFixedLengthStreamingMode(10L);
            throw new RuntimeException("Expected IllegalStateException for chunkLength != -1 and connected == false");
        } catch (IllegalStateException e) {
            // expected
        }

        // Test 3: IllegalArgumentException if contentLength < 0 and chunkLength == -1 and connected == false
        MyHttpURLConnection conn3 = new MyHttpURLConnection(url);
        conn3.chunkLength = -1;
        // connected is false by default
        try {
            conn3.setFixedLengthStreamingMode(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative contentLength");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test 4: Normal case: set fixedContentLengthLong correctly
        MyHttpURLConnection conn4 = new MyHttpURLConnection(url);
        conn4.chunkLength = -1;
        conn4.setFixedLengthStreamingMode(12345L);
        if (conn4.fixedContentLengthLong != 12345L) {
            throw new RuntimeException("fixedContentLengthLong was not set correctly");
        }
    }
}
