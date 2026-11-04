/*
 * @test
 * @summary Verifies setHostnameVerifier rejects null and stores non-null verifiers observably
 * @run main HttpsURLConnectionWitupTest
 */

import java.net.URL;
import java.security.Principal;
import java.security.cert.Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

public class HttpsURLConnectionWitupTest {

    static class TestConnection extends HttpsURLConnection {
        TestConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() {
        }

        @Override
        public void disconnect() {
        }

        @Override
        public boolean usingProxy() {
            return false;
        }

        @Override
        public String getCipherSuite() {
            return "TEST_SUITE";
        }

        @Override
        public Certificate[] getLocalCertificates() {
            return null;
        }

        @Override
        public Certificate[] getServerCertificates() {
            return null;
        }

        public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
            return null;
        }

        public Principal getLocalPrincipal() {
            return null;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static HostnameVerifier verifier(final boolean result) {
        return new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return result;
            }
        };
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://example.com/");

        TestConnection conn = new TestConnection(url);
        HostnameVerifier first = verifier(true);
        conn.setHostnameVerifier(first);
        check(conn.getHostnameVerifier() == first,
              "getHostnameVerifier should return the verifier that was set");

        HostnameVerifier second = verifier(false);
        conn.setHostnameVerifier(second);
        check(conn.getHostnameVerifier() == second,
              "setHostnameVerifier should replace the previous verifier");

        TestConnection conn2 = new TestConnection(url);
        boolean thrown = false;
        try {
            conn2.setHostnameVerifier(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
            check("no HostnameVerifier specified".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(thrown, "setHostnameVerifier(null) must throw IllegalArgumentException");

        TestConnection conn3 = new TestConnection(url);
        HostnameVerifier keep = verifier(true);
        conn3.setHostnameVerifier(keep);
        thrown = false;
        try {
            conn3.setHostnameVerifier(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "setHostnameVerifier(null) must throw after a valid verifier was set");
        check(conn3.getHostnameVerifier() == keep,
              "failed null assignment must not change the previously set verifier");
    }
}
