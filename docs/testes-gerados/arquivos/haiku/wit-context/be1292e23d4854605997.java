/*
 * @test
 * @summary Verify that HttpsURLConnection.setDefaultSSLSocketFactory rejects a null factory with IllegalArgumentException
 * @run main HttpsURLConnectionWitupTest
 */

import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionWitupTest {

    private static final String EXPECTED_MESSAGE = "no default SSLSocketFactory specified";

    public static void main(String[] args) throws Exception {
        testNullFactoryThrowsIllegalArgumentException();
        testNullFactoryRejectedRepeatedly();
    }

    private static void testNullFactoryThrowsIllegalArgumentException() {
        try {
            HttpsURLConnection.setDefaultSSLSocketFactory(null);
            throw new RuntimeException("IllegalArgumentException expected for null factory");
        } catch (IllegalArgumentException e) {
            if (!EXPECTED_MESSAGE.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
    }

    private static void testNullFactoryRejectedRepeatedly() {
        for (int i = 0; i < 3; i++) {
            try {
                HttpsURLConnection.setDefaultSSLSocketFactory(null);
                throw new RuntimeException("IllegalArgumentException expected on iteration " + i);
            } catch (IllegalArgumentException e) {
                if (!EXPECTED_MESSAGE.equals(e.getMessage())) {
                    throw new RuntimeException("Unexpected message on iteration " + i, e);
                }
            }
        }
    }
}
