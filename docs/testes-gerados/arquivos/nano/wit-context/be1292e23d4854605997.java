/*
 * @test
 * @summary Tests for HttpsURLConnection.setDefaultSSLSocketFactory(SSLSocketFactory sf)
 * @run main HttpsURLConnectionWitupTest
 */

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetDefaultSSLSocketFactoryWithNull();
        testSetDefaultSSLSocketFactoryWithValid();
    }

    private static void testSetDefaultSSLSocketFactoryWithNull() {
        boolean exceptionThrown = false;
        try {
            HttpsURLConnection.setDefaultSSLSocketFactory(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException when passing null");
        }
    }

    private static void testSetDefaultSSLSocketFactoryWithValid() {
        SSLSocketFactory factory = SSLSocketFactory.getDefault();
        HttpsURLConnection.setDefaultSSLSocketFactory(factory);
        // No exception expected, test passes if no exception thrown
    }
}
