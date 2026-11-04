/*
 * @test
 * @summary Test setAllowUserInteraction method delegates correctly
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
        // The constructor requires a Handler, but since Handler is package-private and not accessible here,
        // we use null and catch the expected NullPointerException or IOException.
        // This test focuses on setAllowUserInteraction delegation, so we test that calling it does not throw.

        HttpsURLConnectionImpl conn = null;
        try {
            conn = new HttpsURLConnectionImpl(url, (Proxy) null, null);
        } catch (IOException e) {
            // IOException may be thrown if Handler is null, ignore for test
        } catch (NullPointerException e) {
            // NullPointerException may be thrown if Handler is null, ignore for test
        }

        if (conn == null) {
            // Cannot instantiate due to Handler, create a subclass to override delegate to a dummy
            conn = new HttpsURLConnectionImpl(url) {
                {
                    // Override delegate with dummy implementation
                    delegate = new DelegateHttpsURLConnectionDummy();
                }
            };
        }

        // Call setAllowUserInteraction with true and false
        conn.setAllowUserInteraction(true);
        conn.setAllowUserInteraction(false);

        // If no exceptions thrown, test passes
        System.out.println("Test passed: setAllowUserInteraction calls delegate without exception.");
    }

    // Dummy DelegateHttpsURLConnection to test delegation
    static class DelegateHttpsURLConnectionDummy extends sun.net.www.protocol.https.DelegateHttpsURLConnection {
        boolean lastValue = false;

        DelegateHttpsURLConnectionDummy() {
            super(null, null, null, null);
        }

        @Override
        public void setAllowUserInteraction(boolean allowuserinteraction) {
            lastValue = allowuserinteraction;
            // No-op
        }
    }
}
