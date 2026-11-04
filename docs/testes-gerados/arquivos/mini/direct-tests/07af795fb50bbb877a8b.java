/*
 * @test
 * @summary Test sun.net.www.protocol.https.HttpsURLConnectionImpl.setFixedLengthStreamingMode(long)
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.Proxy;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;
import sun.net.www.protocol.https.Handler;
import java.io.IOException;

public class HttpsURLConnectionImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URL
        URL url = new URL("https://example.com/");
        // Use null proxy and default handler
        Handler handler = new Handler();

        // Construct HttpsURLConnectionImpl instance
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, (Proxy) null, handler);

        // Test setFixedLengthStreamingMode with zero
        conn.setFixedLengthStreamingMode(0L);

        // Test setFixedLengthStreamingMode with positive value
        conn.setFixedLengthStreamingMode(12345L);

        // Test setFixedLengthStreamingMode with large value
        conn.setFixedLengthStreamingMode(Long.MAX_VALUE);

        // No exception expected, no direct observable effect to assert
        // Just ensure no exceptions thrown
        System.out.println("setFixedLengthStreamingMode tests passed");
    }
}
