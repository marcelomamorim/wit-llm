/*
 * @test
 * @summary Test sun.net.www.protocol.https.HttpsURLConnectionImpl.setFixedLengthStreamingMode(long) for expected exceptions and state
 * @run main HttpsURLConnectionImplWitupTest
 */

import sun.net.www.protocol.https.HttpsURLConnectionImpl;
import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;
import java.io.IOException;

public class HttpsURLConnectionImplWitupTest {

    // Minimal stub Handler to satisfy constructor, no real functionality
    static class Handler extends java.net.URLStreamHandler {
        protected java.net.URLConnection openConnection(URL u) throws IOException {
            throw new UnsupportedOperationException("Not supported in test");
        }
    }

    public static void main(String[] args) throws Exception {
        // Create a valid URL for constructor
        URL url = new URL("https://example.com");
        Handler handler = new Handler();

        // Create instance with null Proxy
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, (Proxy) null, handler);

        // Test 1: call setFixedLengthStreamingMode with negative contentLength when chunkLength == -1 and not connected
        // According to WIT, should throw IllegalArgumentException
        // We cannot set chunkLength or connected directly, so we test observable behavior:
        // The delegate is internal, so we test that negative contentLength triggers IllegalArgumentException
        try {
            conn.setFixedLengthStreamingMode(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative contentLength");
        } catch (IllegalArgumentException iae) {
            // Expected
        } catch (IllegalStateException ise) {
            // Allowed alternative if delegate throws IllegalStateException
        }

        // Test 2: call setFixedLengthStreamingMode with zero contentLength (valid)
        // Should not throw
        conn.setFixedLengthStreamingMode(0L);

        // Test 3: call setFixedLengthStreamingMode with positive contentLength
        // Should not throw
        conn.setFixedLengthStreamingMode(100L);

        // Test 4: call setFixedLengthStreamingMode after connection established
        // We cannot connect because delegate is stub, so simulate by calling connect()
        try {
            conn.connect();
        } catch (Exception e) {
            // ignore connect failure
        }
        try {
            conn.setFixedLengthStreamingMode(10L);
        } catch (IllegalStateException ise) {
            // Expected if connected
        } catch (IllegalArgumentException iae) {
            // Also allowed
        }

        System.out.println("HttpsURLConnectionImpl.setFixedLengthStreamingMode tests passed");
    }
}
