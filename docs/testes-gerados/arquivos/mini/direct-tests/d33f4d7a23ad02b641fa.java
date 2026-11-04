/*
 * @test
 * @summary Test com.sun.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory behavior
 * @run main com.sun.net.ssl.HttpsURLConnectionWitupTest
 */

import com.sun.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class HttpsURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetDefaultSSLSocketFactoryRejectsNull();
        testSetDefaultSSLSocketFactoryAcceptsValidFactory();
    }

    private static void testSetDefaultSSLSocketFactoryRejectsNull() {
        try {
            HttpsURLConnection.setDefaultSSLSocketFactory(null);
            throw new RuntimeException("Expected IllegalArgumentException for null argument");
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
        // Should not throw
        HttpsURLConnection.setDefaultSSLSocketFactory(factory);
    }
}
