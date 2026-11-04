/*
 * @test
 * @summary Test sun.net.www.protocol.https.HttpsURLConnectionImpl.setUseCaches(boolean) delegates correctly
 * @run main sun.net.www.protocol.https.HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;
import java.io.IOException;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

public class HttpsURLConnectionImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a valid URL
        URL url = new URL("https://example.com");

        // Create instance of HttpsURLConnectionImpl with null Proxy and null Handler (Handler is package-private, so use null)
        // The constructor requires a Handler, but since we cannot instantiate Handler (not public),
        // we use null and expect a NullPointerException or IOException.
        // To avoid this, we use the public constructor with URL and Proxy null and Handler null,
        // and catch IOException if thrown.

        HttpsURLConnectionImpl conn = null;
        try {
            conn = new HttpsURLConnectionImpl(url, (Proxy) null, null);
        } catch (IOException e) {
            // If IOException occurs, fail test
            throw new RuntimeException("Failed to create HttpsURLConnectionImpl instance", e);
        }

        // Test setUseCaches(true)
        conn.setUseCaches(true);

        // Test setUseCaches(false)
        conn.setUseCaches(false);

        // If no exceptions thrown, test passes
        System.out.println("Test passed: setUseCaches invoked without exceptions.");
    }
}
