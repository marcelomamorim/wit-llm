/* @test    @summary Test setIfModifiedSince method of HttpsURLConnectionImpl    @run main HttpsURLConnectionImplWitupTest */

import java.net.URL;
import java.io.IOException;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetIfModifiedSincePositive();
        testSetIfModifiedSinceZero();
        testSetIfModifiedSinceNegative();
    }

    private static void testSetIfModifiedSincePositive() throws IOException {
        URL url = new URL("https://example.com");
        sun.net.www.protocol.https.HttpsURLConnectionImpl conn = new sun.net.www.protocol.https.HttpsURLConnectionImpl(url, null, null);
        long value = 1625097600000L; // specific timestamp
        conn.setIfModifiedSince(value);
        // No exception means success
    }

    private static void testSetIfModifiedSinceZero() throws IOException {
        URL url = new URL("https://example.com");
        sun.net.www.protocol.https.HttpsURLConnectionImpl conn = new sun.net.www.protocol.https.HttpsURLConnectionImpl(url, null, null);
        long value = 0L;
        conn.setIfModifiedSince(value);
        // No exception means success
    }

    private static void testSetIfModifiedSinceNegative() throws IOException {
        URL url = new URL("https://example.com");
        sun.net.www.protocol.https.HttpsURLConnectionImpl conn = new sun.net.www.protocol.https.HttpsURLConnectionImpl(url, null, null);
        long value = -100L;
        conn.setIfModifiedSince(value);
        // No exception means success
    }
}
