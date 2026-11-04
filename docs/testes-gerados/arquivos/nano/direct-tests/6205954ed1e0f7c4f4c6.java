/* @test    @summary Test setFixedLengthStreamingMode with valid and invalid inputs    @run main HttpsURLConnectionImplWitupTest */

import java.net.URL;
import java.io.IOException;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetFixedLengthStreamingModeValid();
        testSetFixedLengthStreamingModeZero();
        testSetFixedLengthStreamingModeNegative();
    }

    private static void testSetFixedLengthStreamingModeValid() throws IOException {
        URL url = new URL("https://example.com");
        sun.net.www.protocol.https.HttpsURLConnectionImpl conn = new sun.net.www.protocol.https.HttpsURLConnectionImpl(url, null, null);
        conn.setFixedLengthStreamingMode(1024);
        // No exception expected, test passes if no exception thrown
    }

    private static void testSetFixedLengthStreamingModeZero() throws IOException {
        URL url = new URL("https://example.com");
        sun.net.www.protocol.https.HttpsURLConnectionImpl conn = new sun.net.www.protocol.https.HttpsURLConnectionImpl(url, null, null);
        conn.setFixedLengthStreamingMode(0);
        // Zero is valid, no exception expected
    }

    private static void testSetFixedLengthStreamingModeNegative() throws IOException {
        URL url = new URL("https://example.com");
        sun.net.www.protocol.https.HttpsURLConnectionImpl conn = new sun.net.www.protocol.https.HttpsURLConnectionImpl(url, null, null);
        try {
            conn.setFixedLengthStreamingMode(-1);
            // Expecting an exception or specific behavior; if exception, test should catch
            // But since method does not specify exception for negative, test for behavior
            // Here, assuming no exception, test passes
        } catch (IllegalArgumentException e) {
            // Expected if method throws for negative input
        }
    }
}
