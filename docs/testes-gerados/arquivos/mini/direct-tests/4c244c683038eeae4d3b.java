/*
 * @test
 * @summary Test HttpsURLConnection.setHostnameVerifier behavior
 * @run main HttpsURLConnectionWitupTest
 */

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.HostnameVerifier;
import java.net.URL;

public class HttpsURLConnectionWitupTest {

    static class DummyHttpsURLConnection extends HttpsURLConnection {
        protected DummyHttpsURLConnection(URL url) {
            super(url);
        }
        public String getCipherSuite() { return null; }
        public java.security.cert.Certificate[] getLocalCertificates() { return null; }
        public java.security.cert.Certificate[] getServerCertificates() { return null; }
        public javax.security.cert.X509Certificate[] getServerCertificateChain() { return null; }
        public java.security.Principal getPeerPrincipal() { return null; }
        public java.security.Principal getLocalPrincipal() { return null; }
        public void connect() { connected = true; }
        public void disconnect() { connected = false; }
        public boolean usingProxy() { return false; }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://example.com");
        DummyHttpsURLConnection conn = new DummyHttpsURLConnection(url);

        // Test that setHostnameVerifier throws IllegalArgumentException on null
        boolean threw = false;
        try {
            conn.setHostnameVerifier(null);
        } catch (IllegalArgumentException e) {
            threw = true;
            if (!"no HostnameVerifier specified".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        }

        // Test that setHostnameVerifier accepts a non-null HostnameVerifier
        HostnameVerifier verifier = new HostnameVerifier() {
            public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
                return true;
            }
        };

        conn.setHostnameVerifier(verifier);

        // No direct getter, so test by reflection to confirm field set (allowed here since no public getter)
        // But per instructions, avoid reflection if possible; so test by calling set again with same verifier
        // and no exception thrown.
        conn.setHostnameVerifier(verifier);

        System.out.println("Test passed");
    }
}
