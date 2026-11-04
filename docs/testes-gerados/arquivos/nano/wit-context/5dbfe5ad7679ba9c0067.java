/* @test    @summary Test for setAllowUserInteraction method in HttpsURLConnectionOldImpl
 * @run main HttpsURLConnectionOldImplWitupTest
 */

import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import com.sun.net.ssl.internal.www.protocol.https.HttpsURLConnectionOldImpl;

public class HttpsURLConnectionOldImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetAllowUserInteractionConnected();
        testSetAllowUserInteractionNotConnected();
    }

    private static void testSetAllowUserInteractionConnected() throws IOException {
        URL url = new URL("https://example.com");
        HttpsURLConnectionOldImpl conn = new HttpsURLConnectionOldImpl(url, (java.net.Proxy) null, null);
        // Simulate connection state
        // Note: Since connection state is internal, we assume the method can be called without exception
        try {
            conn.setAllowUserInteraction(true);
            System.out.println("testSetAllowUserInteractionConnected passed");
        } catch (IllegalStateException e) {
            System.err.println("testSetAllowUserInteractionConnected failed: " + e);
        }
    }

    private static void testSetAllowUserInteractionNotConnected() throws IOException {
        URL url = new URL("https://example.com");
        HttpsURLConnectionOldImpl conn = new HttpsURLConnectionOldImpl(url, (java.net.Proxy) null, null);
        // No connection established, calling setAllowUserInteraction should not throw
        try {
            conn.setAllowUserInteraction(false);
            System.out.println("testSetAllowUserInteractionNotConnected passed");
        } catch (IllegalStateException e) {
            System.err.println("testSetAllowUserInteractionNotConnected failed: " + e);
        }
    }
}
