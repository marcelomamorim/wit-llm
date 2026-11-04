/*
 * @test
 * @summary Verifies that HttpsURLConnection.setSSLSocketFactory rejects null with IllegalArgumentException and accepts a non-null factory
 * @modules java.base/com.sun.net.ssl
 * @run main HttpsURLConnectionWitupTest
 */

import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import com.sun.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionWitupTest {

    static final class TestConnection extends HttpsURLConnection {
        TestConnection(URL url) throws IOException {
            super(url);
        }

        public void connect() {
        }

        public void disconnect() {
        }

        public boolean usingProxy() {
            return false;
        }

        public String getCipherSuite() {
            return null;
        }

        public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://example.com/");
        TestConnection conn = new TestConnection(url);

        boolean thrown = false;
        try {
            conn.setSSLSocketFactory(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
            if (e.getMessage() == null || !e.getMessage().contains("SSLSocketFactory")) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException for null factory");
        }

        thrown = false;
        try {
            conn.setSSLSocketFactory(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException on repeated null call");
        }

        conn.setSSLSocketFactory(SSLSocketFactory.getDefault());
    }
}
