/*
 * @test
 * @summary Test setSSLSocketFactory method of com.sun.net.ssl.HttpsURLConnection
 * @run main HttpsURLConnectionWitupTest
 */

import com.sun.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.net.URL;
import java.io.IOException;

public class HttpsURLConnectionWitupTest {

    static class DummyHttpsURLConnection extends HttpsURLConnection {
        protected DummyHttpsURLConnection(URL url) throws IOException {
            super(url);
        }

        @Override
        public String getCipherSuite() {
            return null;
        }

        @Override
        public java.security.cert.Certificate[] getServerCertificates() {
            return null;
        }

        @Override
        public void connect() throws IOException {
            // no-op
        }

        @Override
        public void disconnect() {
            // no-op
        }

        @Override
        public boolean usingProxy() {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://example.com");
        DummyHttpsURLConnection conn = new DummyHttpsURLConnection(url);

        // Test 1: Passing null should throw IllegalArgumentException
        try {
            conn.setSSLSocketFactory(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test 2: Passing a non-null SSLSocketFactory should set without exception
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        if (factory == null) {
            throw new RuntimeException("Default SSLSocketFactory is null, cannot test");
        }
        conn.setSSLSocketFactory(factory);

        // No direct getter to verify, so test passes if no exception thrown
    }
}
