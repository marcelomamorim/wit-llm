/*
 * @test
 * @summary Test sun.net.www.protocol.https.HttpsURLConnectionImpl.setUseCaches(boolean) behavior
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;
import java.io.IOException;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetUseCachesBeforeConnected();
        testSetUseCachesAfterConnectedThrows();
    }

    // Test that setUseCaches delegates correctly when not connected
    private static void testSetUseCachesBeforeConnected() throws Exception {
        URL url = new URL("https://example.com");
        // Use null proxy and null handler as Handler is package-private and no public factory
        // We use null for handler because constructor requires it but no public Handler available
        // This test only checks that setUseCaches does not throw before connected
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, (Proxy) null, null);

        // Should not throw
        conn.setUseCaches(true);
        conn.setUseCaches(false);
    }

    // Test that setUseCaches throws IllegalStateException if connected
    private static void testSetUseCachesAfterConnectedThrows() throws Exception {
        URL url = new URL("https://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, (Proxy) null, null);

        // Use reflection to set connected = true since connected is protected in superclass
        // connected is protected boolean field in java.net.URLConnection
        java.lang.reflect.Field connectedField = java.net.URLConnection.class.getDeclaredField("connected");
        connectedField.setAccessible(true);
        connectedField.setBoolean(conn, true);

        boolean threw = false;
        try {
            conn.setUseCaches(true);
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when calling setUseCaches after connected");
        }
    }
}
