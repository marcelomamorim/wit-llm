/*
 * @test
 * @summary HttpsURLConnection.setHostnameVerifier rejects null and stores non-null verifiers
 * @run main HttpsURLConnectionWitupTest
 */

import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.security.cert.Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class HttpsURLConnectionWitupTest {

    static class StubConnection extends HttpsURLConnection {
        StubConnection(URL url) {
            super(url);
        }

        @Override
        public String getCipherSuite() {
            return "TLS_STUB";
        }

        @Override
        public Certificate[] getLocalCertificates() {
            return null;
        }

        @Override
        public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
            return null;
        }

        @Override
        public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
            return null;
        }

        @Override
        public Principal getLocalPrincipal() {
            return null;
        }

        @Override
        public void connect() throws IOException {
        }

        @Override
        public void disconnect() {
        }

        @Override
        public boolean usingProxy() {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        StubConnection c = new StubConnection(new URL("https://example.com/"));

        try {
            c.setHostnameVerifier(null);
            throw new RuntimeException("expected IllegalArgumentException for null verifier");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        HostnameVerifier first = (host, session) -> true;
        c.setHostnameVerifier(first);
        if (c.getHostnameVerifier() != first) {
            throw new RuntimeException("stored verifier differs from the one set");
        }

        HostnameVerifier second = (host, session) -> false;
        c.setHostnameVerifier(second);
        if (c.getHostnameVerifier() != second) {
            throw new RuntimeException("verifier was not replaced by second set");
        }

        try {
            c.setHostnameVerifier(null);
            throw new RuntimeException("expected IllegalArgumentException for null verifier");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        if (c.getHostnameVerifier() != second) {
            throw new RuntimeException("rejected null must not change the current verifier");
        }
    }
}
