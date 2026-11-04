/* @test    @summary Test setFixedLengthStreamingMode with various values    @run main HttpsURLConnectionImplWitupTest */

import java.net.URL;
import java.io.IOException;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetFixedLengthStreamingModeZero();
        testSetFixedLengthStreamingModePositive();
        testSetFixedLengthStreamingModeLarge();
    }

    static void testSetFixedLengthStreamingModeZero() throws IOException {
        URL url = new URL("https://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url);
        conn.setFixedLengthStreamingMode(0L);
        // No exception expected, verify delegate method call if accessible
    }

    static void testSetFixedLengthStreamingModePositive() throws IOException {
        URL url = new URL("https://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url);
        conn.setFixedLengthStreamingMode(12345L);
        // No exception expected, verify delegate method call if accessible
    }

    static void testSetFixedLengthStreamingModeLarge() throws IOException {
        URL url = new URL("https://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url);
        long largeValue = Long.MAX_VALUE;
        conn.setFixedLengthStreamingMode(largeValue);
        // No exception expected, verify delegate method call if accessible
    }
}
