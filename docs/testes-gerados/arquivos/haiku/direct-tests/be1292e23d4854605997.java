/*
 * @test
 * @summary Verifies that HttpsURLConnection.setDefaultSSLSocketFactory rejects null and stores non-null factories
 * @run main HttpsURLConnectionWitupTest
 */

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        SSLSocketFactory original = HttpsURLConnection.getDefaultSSLSocketFactory();
        try {
            boolean rejected = false;
            try {
                HttpsURLConnection.setDefaultSSLSocketFactory(null);
            } catch (IllegalArgumentException expected) {
                rejected = true;
            }
            if (!rejected) {
                throw new AssertionError("null SSLSocketFactory was accepted");
            }
            if (HttpsURLConnection.getDefaultSSLSocketFactory() != original) {
                throw new AssertionError("default factory changed after rejected null");
            }

            SSLSocketFactory custom = (SSLSocketFactory) SSLSocketFactory.getDefault();
            HttpsURLConnection.setDefaultSSLSocketFactory(custom);
            if (HttpsURLConnection.getDefaultSSLSocketFactory() != custom) {
                throw new AssertionError("default factory was not updated");
            }
        } finally {
            if (original != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(original);
            }
        }
    }
}
