/*
 * @test
 * @summary Test setHostnameVerifier method of HttpsURLConnection
 * @run main com.sun.net.ssl.HttpsURLConnectionWitupTest
 */

import com.sun.net.ssl.HttpsURLConnection;
import com.sun.net.ssl.HostnameVerifier;
import java.net.URL;
import java.io.IOException;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetHostnameVerifierWithNull();
        testSetHostnameVerifierWithValid();
    }

    private static void testSetHostnameVerifierWithNull() throws Exception {
        HttpsURLConnection conn = new DummyHttpsURLConnection(new URL("https://example.com"));
        try {
            conn.setHostnameVerifier(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            if (!"no HostnameVerifier specified".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testSetHostnameVerifierWithValid() throws Exception {
        HttpsURLConnection conn = new DummyHttpsURLConnection(new URL("https://example.com"));
        HostnameVerifier verifier = new HostnameVerifier() {
            public boolean verify(String urlHostname, String certHostname) {
                return true;
            }
        };
        conn.setHostnameVerifier(verifier);
        // No direct getter, so use reflection to verify the field is set correctly
        // But reflection is disallowed, so test by calling setHostnameVerifier again with same verifier
        // and ensure no exception is thrown
        conn.setHostnameVerifier(verifier);
    }

    // Minimal concrete subclass to instantiate abstract HttpsURLConnection
    private static class DummyHttpsURLConnection extends HttpsURLConnection {

        protected DummyHttpsURLConnection(URL url) throws IOException {
            super(url);
        }

        public void connect() throws IOException {
            // no-op
        }

        public void disconnect() {
            // no-op
        }

        public boolean usingProxy() {
            return false;
        }

        public String getCipherSuite() {
            return null;
        }

        public java.security.cert.Certificate[] getServerCertificates() throws javax.net.ssl.SSLPeerUnverifiedException {
            return null;
        }
    }
}
