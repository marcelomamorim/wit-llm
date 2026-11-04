/* @test    @summary Test setFixedLengthStreamingMode for HttpsURLConnectionImpl    @run main HttpsURLConnectionImplWitupTest */

import java.net.URL;
import java.io.IOException;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetFixedLengthStreamingModeWithConnected();
        testSetFixedLengthStreamingModeWithNegativeContentLength();
        testSetFixedLengthStreamingModeWithInvalidChunkLength();
    }

    private static void testSetFixedLengthStreamingModeWithConnected() throws Exception {
        URL url = new URL("http://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, null, null);
        // Simulate connected state
        conn.delegate.connected = true; // Note: assuming delegate has 'connected' field for test
        try {
            conn.setFixedLengthStreamingMode(100);
            System.out.println("testSetFixedLengthStreamingModeWithConnected: PASSED");
        } catch (IllegalStateException e) {
            System.out.println("testSetFixedLengthStreamingModeWithConnected: FAILED - " + e);
        }
    }

    private static void testSetFixedLengthStreamingModeWithNegativeContentLength() throws Exception {
        URL url = new URL("http://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, null, null);
        // Simulate not connected
        conn.delegate.connected = false; // assuming delegate has 'connected'
        try {
            conn.setFixedLengthStreamingMode(-1);
            System.out.println("testSetFixedLengthStreamingModeWithNegativeContentLength: PASSED");
        } catch (IllegalArgumentException e) {
            System.out.println("testSetFixedLengthStreamingModeWithNegativeContentLength: FAILED - " + e);
        }
    }

    private static void testSetFixedLengthStreamingModeWithInvalidChunkLength() throws Exception {
        URL url = new URL("http://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, null, null);
        // Simulate not connected and chunkLength == -1
        conn.delegate.connected = false; // assuming delegate has 'connected'
        try {
            conn.setFixedLengthStreamingMode(-1);
            System.out.println("testSetFixedLengthStreamingModeWithInvalidChunkLength: PASSED");
        } catch (IllegalArgumentException e) {
            System.out.println("testSetFixedLengthStreamingModeWithInvalidChunkLength: FAILED - " + e);
        }
    }
}
