/* @test    @summary Test for setAllowUserInteraction method in HttpsURLConnectionImpl    @run main HttpsURLConnectionImplWitupTest */

import java.net.URL;
import java.net.MalformedURLException;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetAllowUserInteractionConnected();
        testSetAllowUserInteractionNotConnected();
    }

    private static void testSetAllowUserInteractionConnected() throws Exception {
        URL url = new URL("https://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url);
        // Simulate connected state
        conn.delegate.checkConnected();
        try {
            conn.setAllowUserInteraction(true);
            // Expect IllegalStateException due to connected state
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException");
        }
    }

    private static void testSetAllowUserInteractionNotConnected() throws Exception {
        URL url = new URL("https://example.org");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url);
        // Not connected, should not throw
        try {
            conn.setAllowUserInteraction(true);
            System.out.println("PASS: No exception thrown as expected");
        } catch (IllegalStateException e) {
            System.out.println("FAIL: Unexpected IllegalStateException");
        }
    }
}
