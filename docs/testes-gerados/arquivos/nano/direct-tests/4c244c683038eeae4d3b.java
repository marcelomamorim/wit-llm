/* @test    @summary Test setHostnameVerifier method of HttpsURLConnection    @run main HttpsURLConnectionWitupTest */

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.HostnameVerifier;
import java.net.URL;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetHostnameVerifierWithValidVerifier();
        testSetHostnameVerifierWithNullVerifier();
    }

    private static void testSetHostnameVerifierWithValidVerifier() throws Exception {
        URL url = new URL("https://example.com");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        HostnameVerifier verifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
                return true;
            }
        };
        connection.setHostnameVerifier(verifier);
        // No exception thrown, verifier set successfully
    }

    private static void testSetHostnameVerifierWithNullVerifier() throws Exception {
        URL url = new URL("https://example.com");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        try {
            connection.setHostnameVerifier(null);
            // Fail the test if no exception is thrown
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
            assert e.getMessage().equals("no HostnameVerifier specified");
        }
    }
}
