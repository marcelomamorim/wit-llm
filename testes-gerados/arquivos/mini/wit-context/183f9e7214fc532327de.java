/*
 * @test
 * @summary Test sun.net.www.protocol.https.HttpsURLConnectionImpl.setIfModifiedSince(long) behavior
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.Proxy;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;
import sun.net.www.protocol.https.Handler;
import java.io.IOException;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetIfModifiedSinceBeforeConnect();
        testSetIfModifiedSinceAfterConnectThrows();
    }

    static void testSetIfModifiedSinceBeforeConnect() throws Exception {
        URL url = new URL("https://example.com/");
        Handler handler = new Handler();
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, handler);

        // Before connect, setting ifModifiedSince should not throw
        conn.setIfModifiedSince(123456789L);
    }

    static void testSetIfModifiedSinceAfterConnectThrows() throws Exception {
        URL url = new URL("https://example.com/");
        Handler handler = new Handler();
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, handler);

        // Force connection by calling connect()
        conn.connect();

        boolean threw = false;
        try {
            conn.setIfModifiedSince(987654321L);
        } catch (IllegalStateException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when calling setIfModifiedSince after connect");
        }
    }
}
