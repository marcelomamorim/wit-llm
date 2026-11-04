/*
 * @test
 * @summary Test setIfModifiedSince(long) method of HttpsURLConnectionOldImpl
 * @run main HttpsURLConnectionOldImplWitupTest
 */

import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;
import java.io.IOException;
import com.sun.net.ssl.internal.www.protocol.https.HttpsURLConnectionOldImpl;

public class HttpsURLConnectionOldImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetIfModifiedSinceBeforeConnect();
        testSetIfModifiedSinceAfterConnect();
    }

    private static void testSetIfModifiedSinceBeforeConnect() throws Exception {
        // Create a valid URL
        URL url = new URL("https://example.com");
        // Use null proxy and null handler as no public constructor or factory is available
        // Use reflection to create Handler instance is not possible due to lack of context
        // So we use null for handler as the constructor allows it
        HttpsURLConnectionOldImpl conn = new HttpsURLConnectionOldImpl(url, (Proxy) null, null);

        // Set ifModifiedSince before connected - should not throw
        conn.setIfModifiedSince(123456789L);
    }

    private static void testSetIfModifiedSinceAfterConnect() throws Exception {
        URL url = new URL("https://example.com");
        HttpsURLConnectionOldImpl conn = new HttpsURLConnectionOldImpl(url, (Proxy) null, null);

        // Simulate connection by calling connect()
        // connect() is public in superclass HttpsURLConnection, so call it
        conn.connect();

        // After connected, setIfModifiedSince should throw IllegalStateException
        try {
            conn.setIfModifiedSince(987654321L);
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }
}
