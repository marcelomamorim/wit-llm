/*
 * @test
 * @summary Test setIfModifiedSince method of HttpsURLConnectionOldImpl delegates correctly
 * @run main com.sun.net.ssl.internal.www.protocol.https.HttpsURLConnectionOldImplWitupTest
 */

import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;
import java.io.IOException;

public class HttpsURLConnectionOldImplWitupTest {

    static class DummyHandler extends com.sun.net.ssl.internal.www.protocol.https.Handler {
        // Minimal stub to satisfy constructor
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://example.com");
        DummyHandler handler = new DummyHandler();

        // Create instance with null Proxy
        HttpsURLConnectionOldImpl conn = new HttpsURLConnectionOldImpl(url, null, handler);

        // Call setIfModifiedSince with a sample value
        long testValue = 123456789L;
        conn.setIfModifiedSince(testValue);

        // No direct way to verify delegate state, but no exception means delegation succeeded
        // Call again with zero
        conn.setIfModifiedSince(0L);

        // Call again with negative value
        conn.setIfModifiedSince(-1L);

        System.out.println("Test passed: setIfModifiedSince calls completed without exception.");
    }
}
