/*
 * @test
 * @summary Test setHostnameVerifier method of HttpsURLConnection for null argument rejection and normal assignment
 * @run main com.sun.net.ssl.HttpsURLConnectionWitupTest
 */

import com.sun.net.ssl.HttpsURLConnection;
import com.sun.net.ssl.HostnameVerifier;
import java.net.URL;
import java.io.IOException;

public class HttpsURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetHostnameVerifierRejectsNull();
        testSetHostnameVerifierAcceptsNonNull();
        System.out.println("All tests passed.");
    }

    private static void testSetHostnameVerifierRejectsNull() throws Exception {
        URL url = new URL("https://example.com");
        HttpsURLConnection conn = new DummyHttpsURLConnection(url);
        try {
            conn.setHostnameVerifier(null);
            throw new RuntimeException("Expected IllegalArgumentException for null argument");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testSetHostnameVerifierAcceptsNonNull() throws Exception {
        URL url = new URL("https://example.com");
        HttpsURLConnection conn = new DummyHttpsURLConnection(url);
        HostnameVerifier verifier = new HostnameVerifier() {
            public boolean verify(String urlHostname, String certHostname) {
                return true;
            }
        };
        conn.setHostnameVerifier(verifier);
        // No direct getter, so we rely on no exception and no crash
    }

    // Minimal concrete subclass to instantiate abstract HttpsURLConnection
    private static class DummyHttpsURLConnection extends HttpsURLConnection {
        protected DummyHttpsURLConnection(URL url) throws IOException {
            super(url);
        }

        public String getCipherSuite() {
            return null;
        }

        public java.security.cert.Certificate[] getServerCertificates() throws javax.net.ssl.SSLPeerUnverifiedException {
            return null;
        }

        public void connect() throws IOException {}

        public void disconnect() {}

        public boolean usingProxy() {
            return false;
        }
    }
}
