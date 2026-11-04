/*
 * @test
 * @summary Verify HttpsURLConnection.setHostnameVerifier rejects null and stores non-null verifiers
 * @modules java.base/com.sun.net.ssl
 * @run main HttpsURLConnectionWitupTest
 */

import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import com.sun.net.ssl.HostnameVerifier;
import com.sun.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionWitupTest {

    static class TestConnection extends HttpsURLConnection {
        TestConnection(URL url) throws IOException {
            super(url);
        }

        public String getCipherSuite() {
            return null;
        }

        public Certificate[] getServerCertificates() {
            return null;
        }

        public void connect() {
        }

        public void disconnect() {
        }

        public boolean usingProxy() {
            return false;
        }

        HostnameVerifier current() {
            return hostnameVerifier;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://localhost/");

        TestConnection conn = new TestConnection(url);
        HostnameVerifier defaultVerifier = conn.current();
        check(defaultVerifier != null, "default verifier must not be null");
        check(!defaultVerifier.verify("a.example", "b.example"),
              "default verifier must deny mismatched hostnames");

        HostnameVerifier accepting = new HostnameVerifier() {
            public boolean verify(String urlHostname, String certHostname) {
                return true;
            }
        };
        conn.setHostnameVerifier(accepting);
        check(conn.current() == accepting, "non-null verifier must be stored");

        boolean thrown = false;
        try {
            conn.setHostnameVerifier(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "null verifier must throw IllegalArgumentException");
        check(conn.current() == accepting,
              "rejected null must not replace the previous verifier");

        TestConnection fresh = new TestConnection(url);
        thrown = false;
        try {
            fresh.setHostnameVerifier(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "null verifier on a fresh connection must throw IllegalArgumentException");
        check(fresh.current() == defaultVerifier,
              "rejected null must keep the default verifier");
    }
}
