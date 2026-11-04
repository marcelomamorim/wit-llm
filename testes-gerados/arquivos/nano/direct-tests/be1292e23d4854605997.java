/* @test    @summary Test for HttpsURLConnection.setDefaultSSLSocketFactory method    @run main HttpsURLConnectionWitupTest */

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.HttpsURLConnection;
import java.security.Security;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetDefaultSSLSocketFactory();
    }

    public static void testSetDefaultSSLSocketFactory() {
        SSLSocketFactory factory = SSLSocketFactory.getDefault();
        try {
            HttpsURLConnection.setDefaultSSLSocketFactory(factory);
            // Verify that the default factory is set correctly
            SSLSocketFactory currentFactory = HttpsURLConnection.getDefaultSSLSocketFactory();
            if (currentFactory != factory) {
                throw new AssertionError("Default SSLSocketFactory was not set correctly.");
            }
        } catch (Exception e) {
            throw new AssertionError("Exception during setDefaultSSLSocketFactory: " + e);
        }
    }
}
