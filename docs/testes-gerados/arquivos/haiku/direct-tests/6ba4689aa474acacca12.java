/*
 * @test
 * @summary Verify HttpsURLConnection.setHostnameVerifier accepts non-null verifiers and rejects null
 * @run main HttpsURLConnectionWitupTest
 */

import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;
import com.sun.net.ssl.HostnameVerifier;
import com.sun.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionWitupTest {

    static final class StubConnection extends HttpsURLConnection {
        StubConnection(URL url) throws IOException {
            super(url);
        }

        HostnameVerifier current() {
            return hostnameVerifier;
        }

        public String getCipherSuite() {
            return "TLS_NULL";
        }

        public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
            return null;
        }

        public void connect() throws IOException {
        }

        public void disconnect() {
        }

        public boolean usingProxy() {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://example.com/");
        StubConnection c = new StubConnection(url);

        HostnameVerifier v1 = new HostnameVerifier() {
            public boolean verify(String urlHostname, String certHostname) {
                return true;
            }
        };
        c.setHostnameVerifier(v1);
        if (c.current() != v1) {
            throw new RuntimeException("verifier not set after non-null call");
        }

        boolean thrown = false;
        try {
            c.setHostnameVerifier(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
            if (!"no HostnameVerifier specified".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for null");
        }
        if (c.current() != v1) {
            throw new RuntimeException("null call changed the previous verifier");
        }

        HostnameVerifier v2 = new HostnameVerifier() {
            public boolean verify(String urlHostname, String certHostname) {
                return false;
            }
        };
        c.setHostnameVerifier(v2);
        if (c.current() != v2) {
            throw new RuntimeException("verifier not replaced by second non-null call");
        }
    }
}
