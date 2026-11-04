/*
 * @test
 * @summary Test javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(SSLSocketFactory sf) for null argument and normal usage
 * @run main HttpsURLConnectionWitupTest
 */

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class HttpsURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetDefaultSSLSocketFactoryNull();
        testSetDefaultSSLSocketFactoryValid();
    }

    private static void testSetDefaultSSLSocketFactoryNull() {
        try {
            HttpsURLConnection.setDefaultSSLSocketFactory(null);
            throw new RuntimeException("Expected IllegalArgumentException for null argument");
        } catch (IllegalArgumentException iae) {
            // Expected exception
        } catch (SecurityException se) {
            // Allowed if security manager denies setFactory
        }
    }

    private static void testSetDefaultSSLSocketFactoryValid() {
        SSLSocketFactory factory = SSLSocketFactory.getDefault();
        if (factory == null) {
            // Defensive: if default factory is null, skip test
            return;
        }
        try {
            HttpsURLConnection.setDefaultSSLSocketFactory(factory);
        } catch (SecurityException se) {
            // Allowed if security manager denies setFactory
        }
    }
}
