/* @test    @summary Test for setUseCaches method in HttpsURLConnectionImpl to verify behavior when connection is already established.    @run main HttpsURLConnectionImplWitupTest */

import java.net.URL;
import java.io.IOException;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetUseCachesWhenConnected();
        testSetUseCachesWhenNotConnected();
    }

    private static void testSetUseCachesWhenConnected() throws Exception {
        URL url = new URL("https://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, null);
        // Simulate connection established
        conn.connect();
        try {
            conn.setUseCaches(true);
            // Expecting IllegalStateException due to connection established
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testSetUseCachesWhenNotConnected() throws Exception {
        URL url = new URL("https://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, null);
        // Not connecting, should succeed
        try {
            conn.setUseCaches(true);
            System.out.println("PASS: setUseCaches succeeded when not connected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception when not connected: " + e);
        }
    }
}
