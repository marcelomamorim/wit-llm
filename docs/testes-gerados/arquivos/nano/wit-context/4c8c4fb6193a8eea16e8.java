/* @test    @summary Test setDoOutput method for HttpsURLConnectionImpl to observe behavior when called in different states    @run main HttpsURLConnectionImplWitupTest */

import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetDoOutputWhenConnected();
        testSetDoOutputWhenNotConnected();
    }

    private static void testSetDoOutputWhenConnected() throws Exception {
        URL url = new URL("http://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, null, null);
        // Simulate connection established
        // Note: Since connection state is internal, we assume calling connect() sets connected
        conn.connect();
        try {
            conn.setDoOutput(true);
            // If no exception, test passes for this scenario
        } catch (IllegalStateException e) {
            throw new AssertionError("Unexpected IllegalStateException when connected", e);
        }
    }

    private static void testSetDoOutputWhenNotConnected() throws Exception {
        URL url = new URL("http://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, null, null);
        // Not connecting explicitly
        try {
            conn.setDoOutput(true);
            // Expect no exception in current implementation
        } catch (IllegalStateException e) {
            throw new AssertionError("Unexpected IllegalStateException when not connected", e);
        }
    }
}
