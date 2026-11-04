/*
 * @test
 * @summary Test setAllowUserInteraction(boolean) method of HttpsURLConnectionOldImpl
 * @run main com.sun.net.ssl.internal.www.protocol.https.HttpsURLConnectionOldImplWitupTest
 */

import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;
import java.io.IOException;
import com.sun.net.ssl.internal.www.protocol.https.HttpsURLConnectionOldImpl;

public class HttpsURLConnectionOldImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetAllowUserInteractionBeforeConnected();
        testSetAllowUserInteractionAfterConnected();
    }

    private static void testSetAllowUserInteractionBeforeConnected() throws Exception {
        // Create a valid URL
        URL url = new URL("https://example.com");
        // Use null proxy and null handler as no public constructor or factory is available
        // We use reflection to create a Handler instance is not possible here, so pass null
        HttpsURLConnectionOldImpl conn = new HttpsURLConnectionOldImpl(url, (Proxy) null, null);

        // Initially, connection is not connected, so setAllowUserInteraction should succeed
        conn.setAllowUserInteraction(true);
        conn.setAllowUserInteraction(false);
    }

    private static void testSetAllowUserInteractionAfterConnected() throws Exception {
        URL url = new URL("https://example.com");
        HttpsURLConnectionOldImpl conn = new HttpsURLConnectionOldImpl(url, (Proxy) null, null);

        // Force the connection to be connected by calling getInputStream or similar
        // But since delegate and internals are not accessible, we simulate by calling connect()
        conn.connect();

        // After connected, setAllowUserInteraction should throw IllegalStateException
        boolean thrown = false;
        try {
            conn.setAllowUserInteraction(true);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException when calling setAllowUserInteraction after connected");
        }
    }
}
