/*
 * @test
 * @summary HttpsURLConnection.setSSLSocketFactory rejects null and accepts a non-null SSLSocketFactory
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

    static class TestConnection extends HttpsURLConnection {
        TestConnection(URL url) throws IOException {
            super(url);
        }

        public String getCipherSuite() {
            return null;
        }

        public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
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
        URL url = new URL("https://localhost/");
        TestConnection conn = new TestConnection(url);

        boolean iaeThrown = false;
        try {
            conn.setSSLSocketFactory(null);
        } catch (IllegalArgumentException e) {
            iaeThrown = true;
        }
        if (!iaeThrown) {
            throw new RuntimeException("Expected IllegalArgumentException for null factory");
        }

        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        conn.setSSLSocketFactory(factory);

        conn.setSSLSocketFactory(factory);

        TestConnection conn2 = new TestConnection(url);
        conn2.setSSLSocketFactory(factory);
    }
}
