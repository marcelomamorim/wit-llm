/*
 * @test
 * @summary Test setSSLSocketFactory method of HttpsURLConnection
 * @run main com.sun.net.ssl.HttpsURLConnectionWitupTest
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

        public String getCipherSuite() {
            return null;
        }

        public java.security.cert.Certificate[] getServerCertificates() {
            return null;
        }

        public void connect() throws IOException {}

        public void disconnect() {}

        public boolean usingProxy() {
            return false;
        }
    }

    static class DummySSLSocketFactory extends SSLSocketFactory {
        public String[] getDefaultCipherSuites() { return new String[0]; }
        public String[] getSupportedCipherSuites() { return new String[0]; }
        public java.net.Socket createSocket(java.net.Socket s, String host, int port, boolean autoClose) { return null; }
        public java.net.Socket createSocket(String host, int port) { return null; }
        public java.net.Socket createSocket(String host, int port, java.net.InetAddress localHost, int localPort) { return null; }
        public java.net.Socket createSocket(java.net.InetAddress host, int port) { return null; }
        public java.net.Socket createSocket(java.net.InetAddress address, int port, java.net.InetAddress localAddress, int localPort) { return null; }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://example.com");
        DummyHttpsURLConnection conn = new DummyHttpsURLConnection(url);

        // Test that passing null throws IllegalArgumentException
        try {
            conn.setSSLSocketFactory(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            if (!"no SSLSocketFactory specified".equals(e.getMessage())) {
                throw new RuntimeException("IllegalArgumentException message mismatch");
            }
        }

        // Test setting a valid SSLSocketFactory
        DummySSLSocketFactory factory = new DummySSLSocketFactory();
        conn.setSSLSocketFactory(factory);

        // No direct getter to verify sslSocketFactory, but no exception means success
    }
}
