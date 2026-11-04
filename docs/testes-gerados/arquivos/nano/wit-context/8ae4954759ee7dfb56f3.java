/* @test    @summary Test setFixedLengthStreamingMode for HttpURLConnection    @run main HttpURLConnectionWitupTest */

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetFixedLengthStreamingMode_AlreadyConnected();
        testSetFixedLengthStreamingMode_ChunkedEncodingSet();
        testSetFixedLengthStreamingMode_InvalidContentLength();
    }

    private static void testSetFixedLengthStreamingMode_AlreadyConnected() throws IOException {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // Simulate connection established
        setConnected(conn);
        try {
            conn.setFixedLengthStreamingMode(100);
            // Expect IllegalStateException
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException");
        }
    }

    private static void testSetFixedLengthStreamingMode_ChunkedEncodingSet() throws IOException {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // Set chunkLength to simulate chunked encoding mode
        setChunkLength(conn, 1024);
        try {
            conn.setFixedLengthStreamingMode(50);
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException");
        }
    }

    private static void testSetFixedLengthStreamingMode_InvalidContentLength() throws IOException {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // Ensure not connected and chunkLength is -1
        clearConnectionState(conn);
        try {
            conn.setFixedLengthStreamingMode(-10);
            System.out.println("FAIL: Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException");
        }
    }

    // Helper methods to simulate internal state changes
    private static void setConnected(HttpURLConnection conn) {
        // Reflection or package-private access would be needed in real test; here, assume direct access
        // For illustration, assume a method or field exists to set connected state
        // Since actual code does not expose, this is a placeholder
        // e.g., ((HttpURLConnection) conn).connected = true;
    }

    private static void setChunkLength(HttpURLConnection conn, int length) {
        // Similar placeholder for setting chunkLength
    }

    private static void clearConnectionState(HttpURLConnection conn) {
        // Reset internal state to not connected and chunkLength to -1
    }
}
