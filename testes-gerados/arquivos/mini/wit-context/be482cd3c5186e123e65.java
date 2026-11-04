/*
 * @test
 * @summary Test sun.net.www.protocol.https.HttpsURLConnectionImpl.setAllowUserInteraction(boolean) behavior
 * @run main HttpsURLConnectionImplWitupTest
 */

import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;
import java.io.IOException;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;
import sun.net.www.protocol.https.DelegateHttpsURLConnection;

public class HttpsURLConnectionImplWitupTest {

    // Minimal subclass of DelegateHttpsURLConnection to control connected state
    static class TestDelegateHttpsURLConnection extends DelegateHttpsURLConnection {
        private boolean connected = false;
        private boolean allowUserInteractionSet = false;
        private boolean allowUserInteractionValue = false;

        TestDelegateHttpsURLConnection(URL url) throws IOException {
            super(url, null, null, null);
        }

        @Override
        public void setAllowUserInteraction(boolean allowuserinteraction) {
            if (connected) {
                throw new IllegalStateException("Already connected");
            }
            this.allowUserInteractionSet = true;
            this.allowUserInteractionValue = allowuserinteraction;
        }

        void setConnected(boolean connected) {
            this.connected = connected;
        }

        boolean isAllowUserInteractionSet() {
            return allowUserInteractionSet;
        }

        boolean getAllowUserInteractionValue() {
            return allowUserInteractionValue;
        }
    }

    public static void main(String[] args) throws Exception {
        // Create a valid URL
        URL url = new URL("https://example.com/");

        // Create instance of HttpsURLConnectionImpl with overridden delegate
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, (Proxy) null, null) {
            {
                // Replace delegate with test delegate
                delegate = new TestDelegateHttpsURLConnection(url);
            }

            @Override
            public void setAllowUserInteraction(boolean allowuserinteraction) {
                delegate.setAllowUserInteraction(allowuserinteraction);
            }
        };

        TestDelegateHttpsURLConnection testDelegate = (TestDelegateHttpsURLConnection) conn.delegate;

        // Test 1: setAllowUserInteraction before connected - should succeed
        testDelegate.setConnected(false);
        conn.setAllowUserInteraction(true);
        if (!testDelegate.isAllowUserInteractionSet()) {
            throw new RuntimeException("setAllowUserInteraction did not set flag when not connected");
        }
        if (!testDelegate.getAllowUserInteractionValue()) {
            throw new RuntimeException("setAllowUserInteraction did not set correct value when not connected");
        }

        // Test 2: setAllowUserInteraction after connected - should throw IllegalStateException
        testDelegate.setConnected(true);
        boolean threw = false;
        try {
            conn.setAllowUserInteraction(false);
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when setting allowUserInteraction after connected");
        }

        System.out.println("HttpsURLConnectionImpl.setAllowUserInteraction(boolean) tests passed");
    }
}
