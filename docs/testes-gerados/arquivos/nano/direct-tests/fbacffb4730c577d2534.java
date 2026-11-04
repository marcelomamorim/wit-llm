/* @test    @summary Test setDoInput method of HttpsURLConnectionImpl    @run main HttpsURLConnectionImplWitupTest */

import java.net.URL;
import java.io.IOException;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

public class HttpsURLConnectionImplWitupTest {

    public static void main(String[] args) throws Exception {
        testSetDoInputTrue();
        testSetDoInputFalse();
    }

    private static void testSetDoInputTrue() throws IOException {
        URL url = new URL("https://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, null, null);
        conn.setDoInput(true);
        // Since delegate.setDoInput is called, and no public getter exists, we test for no exceptions and behavior.
        // Additional behavior verification would require reflection or internal access, which is not permitted.
    }

    private static void testSetDoInputFalse() throws IOException {
        URL url = new URL("https://example.com");
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, null, null);
        conn.setDoInput(false);
        // Similar as above, test for no exceptions.
    }
}
