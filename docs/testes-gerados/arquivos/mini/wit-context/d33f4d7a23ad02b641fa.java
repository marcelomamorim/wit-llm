/*
 * @test
 * @summary Test setDefaultSSLSocketFactory method of HttpsURLConnection
 * @run main HttpsURLConnectionWitupTest
 */

import com.sun.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class HttpsURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetDefaultSSLSocketFactoryNull();
        testSetDefaultSSLSocketFactoryValid();
        System.out.println("All tests passed.");
    }

    private static void testSetDefaultSSLSocketFactoryNull() {
        try {
            HttpsURLConnection.setDefaultSSLSocketFactory(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException iae) {
            // Expected exception
            String msg = iae.getMessage();
            if (msg == null || !msg.contains("no default SSLSocketFactory specified")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }

    private static void testSetDefaultSSLSocketFactoryValid() {
        SSLSocketFactory factory = SSLSocketFactory.getDefault();
        if (factory == null) {
            // Defensive: if default factory is null, skip test
            return;
        }
        // Should not throw
        HttpsURLConnection.setDefaultSSLSocketFactory(factory);
    }
}
