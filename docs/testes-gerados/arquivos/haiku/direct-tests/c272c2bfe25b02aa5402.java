/*
 * @test
 * @summary Tests setDefaultHostnameVerifier null rejection and inheritance of the default verifier by new instances
 * @modules java.base/com.sun.net.ssl
 * @run main HttpsURLConnectionWitupTest
 */

import java.io.IOException;
import java.net.URL;
import com.sun.net.ssl.HostnameVerifier;
import com.sun.net.ssl.HttpsURLConnection;
import java.security.cert.Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;

public class HttpsURLConnectionWitupTest {

    static class Impl extends HttpsURLConnection {
        Impl(URL url) throws IOException {
            super(url);
        }

        HostnameVerifier current() {
            return hostnameVerifier;
        }

        @Override
        public String getCipherSuite() {
            return "TLS_NULL";
        }

        @Override
        public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
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
        URL url = new URL("https://example.com/");

        Impl first = new Impl(url);
        HostnameVerifier original = first.current();
        if (original == null) {
            throw new RuntimeException("initial default verifier is null");
        }

        HostnameVerifier v = new HostnameVerifier() {
            public boolean verify(String urlHostname, String certHostname) {
                return true;
            }
        };

        try {
            HttpsURLConnection.setDefaultHostnameVerifier(null);
            throw new RuntimeException("expected IllegalArgumentException for null verifier");
        } catch (IllegalArgumentException expected) {
        }

        if (new Impl(url).current() != original) {
            throw new RuntimeException("null rejection changed the default verifier");
        }

        HttpsURLConnection.setDefaultHostnameVerifier(v);
        if (new Impl(url).current() != v) {
            throw new RuntimeException("new instance did not inherit the set default verifier");
        }

        try {
            HttpsURLConnection.setDefaultHostnameVerifier(null);
            throw new RuntimeException("expected IllegalArgumentException for null verifier");
        } catch (IllegalArgumentException expected) {
        }

        if (new Impl(url).current() != v) {
            throw new RuntimeException("null rejection replaced the previously set verifier");
        }

        HttpsURLConnection.setDefaultHostnameVerifier(original);
        if (new Impl(url).current() != original) {
            throw new RuntimeException("failed to restore original default verifier");
        }
    }
}
