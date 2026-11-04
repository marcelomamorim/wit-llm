/*
 * @test
 * @summary Test javax.net.ssl.HttpsURLConnection.setHostnameVerifier(HostnameVerifier) for IllegalArgumentException on null input
 * @run main HttpsURLConnectionWitupTest
 */

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.HostnameVerifier;
import java.net.URL;

public class HttpsURLConnectionWitupTest {

    // Minimal concrete subclass to instantiate abstract HttpsURLConnection
    static class TestHttpsURLConnection extends HttpsURLConnection {
        protected TestHttpsURLConnection(URL url) {
            super(url);
        }

        public String getCipherSuite() {
            return null;
        }

        public java.security.cert.Certificate[] getLocalCertificates() {
            return null;
        }

        public java.security.cert.Certificate[] getServerCertificates() {
            return null;
        }

        public Principal getPeerPrincipal() {
            return null;
        }

        public Principal getLocalPrincipal() {
            return null;
        }

        public void connect() {
        }

        public void disconnect() {
        }

        public boolean usingProxy() {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://example.com");
        TestHttpsURLConnection conn = new TestHttpsURLConnection(url);

        // Test that setHostnameVerifier throws IllegalArgumentException if null is passed
        try {
            conn.setHostnameVerifier(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test that setHostnameVerifier accepts a non-null HostnameVerifier
        HostnameVerifier verifier = new HostnameVerifier() {
            public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
                return true;
            }
        };
        conn.setHostnameVerifier(verifier);

        // No direct getter for hostnameVerifier, so no further assert possible
    }
}
