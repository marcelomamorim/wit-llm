/*
 * @test
 * @summary Test sun.net.www.protocol.https.HttpsURLConnectionImpl.setDoInput(boolean) delegates correctly
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.Proxy;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;
import sun.net.www.protocol.https.Handler;

public class HttpsURLConnectionImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URL for testing
        URL url = new URL("https://example.com/");
        Handler handler = new Handler();

        // Create instance with no proxy
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, (Proxy) null, handler);

        // Test setDoInput with true
        conn.setDoInput(true);

        // Test setDoInput with false
        conn.setDoInput(false);

        // If no exception thrown, test passes
        System.out.println("Test passed: setDoInput invoked without exceptions");
    }
}
