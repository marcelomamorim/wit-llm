/*
 * @test
 * @summary Test HttpURLConnection.setFixedLengthStreamingMode(long) behavior
 * @run main HttpURLConnectionWitupTest
 */

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetFixedLengthStreamingModeValid();
        testSetFixedLengthStreamingModeNegativeLength();
        testSetFixedLengthStreamingModeAfterConnected();
        testSetFixedLengthStreamingModeWithChunkedEncoding();
    }

    static void testSetFixedLengthStreamingModeValid() throws Exception {
        HttpURLConnection conn = new DummyHttpURLConnection(new URL("http://example.com"));
        conn.setFixedLengthStreamingMode(123L);
        if (conn.fixedContentLengthLong != 123L) {
            throw new RuntimeException("fixedContentLengthLong not set correctly");
        }
    }

    static void testSetFixedLengthStreamingModeNegativeLength() throws Exception {
        HttpURLConnection conn = new DummyHttpURLConnection(new URL("http://example.com"));
        try {
            conn.setFixedLengthStreamingMode(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testSetFixedLengthStreamingModeAfterConnected() throws Exception {
        HttpURLConnection conn = new DummyHttpURLConnection(new URL("http://example.com"));
        conn.connected = true;
        try {
            conn.setFixedLengthStreamingMode(10L);
            throw new RuntimeException("Expected IllegalStateException when already connected");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    static void testSetFixedLengthStreamingModeWithChunkedEncoding() throws Exception {
        HttpURLConnection conn = new DummyHttpURLConnection(new URL("http://example.com"));
        conn.chunkLength = 100;
        try {
            conn.setFixedLengthStreamingMode(10L);
            throw new RuntimeException("Expected IllegalStateException when chunked encoding streaming mode set");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    // Minimal concrete subclass to instantiate abstract HttpURLConnection
    static class DummyHttpURLConnection extends HttpURLConnection {
        protected DummyHttpURLConnection(URL u) {
            super(u);
        }

        @Override
        public void disconnect() {}

        @Override
        public boolean usingProxy() { return false; }

        @Override
        public void connect() {}
    }
}
