/*
 * @test
 * @summary Test sun.net.www.protocol.https.HttpsURLConnectionImpl.setFixedLengthStreamingMode(int)
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
        conn.setFixedLengthStreamingMode(0);

        // Test setFixedLengthStreamingMode with positive value
        conn.setFixedLengthStreamingMode(12345);

        // Test setFixedLengthStreamingMode with negative value
        // According to the source, no exception is thrown here explicitly
        // so just call and ensure no exception is thrown
        conn.setFixedLengthStreamingMode(-1);

        System.out.println("All setFixedLengthStreamingMode tests passed.");
    }
}
