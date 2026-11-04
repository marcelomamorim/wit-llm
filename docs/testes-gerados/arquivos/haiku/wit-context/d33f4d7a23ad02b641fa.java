/*
 * @test
 * @summary Verifies that setDefaultSSLSocketFactory rejects null and accepts a non-null SSLSocketFactory
 * @modules java.base/com.sun.net.ssl
 * @run main HttpsURLConnectionWitupTest
 */

import com.sun.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testNullFactoryThrowsIllegalArgumentException();
        testNonNullFactoryIsAccepted();
    }

    private static void testNullFactoryThrowsIllegalArgumentException() {
        try {
            HttpsURLConnection.setDefaultSSLSocketFactory(null);
            throw new AssertionError("expected IllegalArgumentException for null factory");
        } catch (IllegalArgumentException expected) {
            if (expected.getMessage() == null
                    || !expected.getMessage().contains("no default SSLSocketFactory specified")) {
                throw new AssertionError("unexpected message: " + expected.getMessage());
            }
        }
    }

    private static void testNonNullFactoryIsAccepted() throws Exception {
        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        HttpsURLConnection.setDefaultSSLSocketFactory(sf);
    }
}
