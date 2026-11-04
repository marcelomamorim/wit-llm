/* @test    @summary Test setHostnameVerifier method of HttpsURLConnection    @run main HttpsURLConnectionWitupTest */

import java.net.URL;
import com.sun.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetHostnameVerifierWithValidVerifier();
        testSetHostnameVerifierWithNull();
    }

    private static void testSetHostnameVerifierWithValidVerifier() {
        HttpsURLConnection connection = createConnection();
        try {
            HostnameVerifier verifier = new HostnameVerifier() {
                public boolean verify(String hostname, String certHostname) {
                    return true;
                }
            };
            connection.setHostnameVerifier(verifier);
            assert connection.hostnameVerifier == verifier : "Verifier was not set correctly";
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    private static void testSetHostnameVerifierWithNull() {
        HttpsURLConnection connection = createConnection();
        try {
            connection.setHostnameVerifier(null);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    private static HttpsURLConnection createConnection() {
        try {
            URL url = new URL("https://example.com");
            return (HttpsURLConnection) url.openConnection();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create connection", e);
        }
    }
}
