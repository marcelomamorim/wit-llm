/*
 * @test
 * @summary Test javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory behavior
 * @run main HttpsURLConnectionWitupTest
 */

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class HttpsURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetDefaultSSLSocketFactoryRejectsNull();
        testSetDefaultSSLSocketFactoryAcceptsValidFactory();
    }

    private static void testSetDefaultSSLSocketFactoryRejectsNull() {
        try {
            HttpsURLConnection.setDefaultSSLSocketFactory(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("no default SSLSocketFactory specified")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }

    private static void testSetDefaultSSLSocketFactoryAcceptsValidFactory() {
        SSLSocketFactory factory = SSLSocketFactory.getDefault();
        if (factory == null) {
            throw new RuntimeException("SSLSocketFactory.getDefault() returned null, cannot test");
        }
        // Should not throw any exception
        HttpsURLConnection.setDefaultSSLSocketFactory(factory);
    }
}
