/*
 * @test
 * @summary Test setAllowUserInteraction method delegates correctly
 * @run main com.sun.net.ssl.internal.www.protocol.https.HttpsURLConnectionOldImplWitupTest
 */

import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;
import java.io.IOException;

public class HttpsURLConnectionOldImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetAllowUserInteraction();
    }

    private static void testSetAllowUserInteraction() throws Exception {
        // Create a dummy URL
        URL url = new URL("https://example.com");

        // Create instance of HttpsURLConnectionOldImpl with null Proxy and null Handler (Handler is package-private, so use null)
        // The constructor requires a Handler, but since Handler is not public and no factory is provided,
        // we use null and catch the expected NullPointerException or IOException.
        // This test focuses on calling setAllowUserInteraction and verifying no exception is thrown.

        HttpsURLConnectionOldImpl conn = null;
        try {
            conn = new HttpsURLConnectionOldImpl(url, (Proxy) null, null);
        } catch (IOException e) {
            // IOException can be thrown by constructor, fail test if so
            throw new RuntimeException("Constructor threw IOException", e);
        } catch (NullPointerException npe) {
            // Handler is null, may cause NPE internally, fail test
            throw new RuntimeException("Constructor threw NullPointerException", npe);
        }

        // Call setAllowUserInteraction with true and false
        conn.setAllowUserInteraction(true);
        conn.setAllowUserInteraction(false);

        // No observable state or return, test passes if no exception thrown
    }
}
