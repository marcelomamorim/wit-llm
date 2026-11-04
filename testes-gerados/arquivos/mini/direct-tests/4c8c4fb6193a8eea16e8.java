/*
 * @test
 * @summary Test sun.net.www.protocol.https.HttpsURLConnectionImpl.setDoOutput(boolean) delegates correctly
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

        // Create instance with null proxy and null handler (handler is package private, so use null)
        // This constructor is protected, so use reflection to instantiate
        HttpsURLConnectionImpl conn = new HttpsURLConnectionImpl(url, (Proxy)null, null) {
            // Override delegate to track setDoOutput call
            protected DelegateHttpsURLConnection delegate = new DelegateHttpsURLConnection(url, null, null, this) {
                boolean doOutputSet = false;
                boolean lastValue = false;
                @Override
                public void setDoOutput(boolean dooutput) {
                    doOutputSet = true;
                    lastValue = dooutput;
                }
            };
        };

        // Initially call setDoOutput(true) and verify delegate is called
        conn.setDoOutput(true);
        if (!((DelegateHttpsURLConnection)conn.delegate).doOutputSet) {
            throw new RuntimeException("Delegate setDoOutput(true) was not called");
        }
        if (!((DelegateHttpsURLConnection)conn.delegate).lastValue) {
            throw new RuntimeException("Delegate setDoOutput(true) did not receive true");
        }

        // Call setDoOutput(false) and verify delegate is called
        ((DelegateHttpsURLConnection)conn.delegate).doOutputSet = false;
        conn.setDoOutput(false);
        if (!((DelegateHttpsURLConnection)conn.delegate).doOutputSet) {
            throw new RuntimeException("Delegate setDoOutput(false) was not called");
        }
        if (((DelegateHttpsURLConnection)conn.delegate).lastValue) {
            throw new RuntimeException("Delegate setDoOutput(false) did not receive false");
        }

        System.out.println("Test passed");
    }

    // Minimal stub for DelegateHttpsURLConnection to allow compilation
    static class DelegateHttpsURLConnection {
        boolean doOutputSet = false;
        boolean lastValue = false;
        DelegateHttpsURLConnection(URL url, Proxy p, Object handler, HttpsURLConnectionImpl parent) {
        }
        public void setDoOutput(boolean dooutput) {
            doOutputSet = true;
            lastValue = dooutput;
        }
    }
}
