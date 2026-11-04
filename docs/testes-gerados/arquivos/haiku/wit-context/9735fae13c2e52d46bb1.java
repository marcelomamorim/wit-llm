/*
 * @test
 * @summary Verifies that HttpsURLConnection.setDefaultHostnameVerifier rejects null with IllegalArgumentException and accepts a non-null verifier
 * @run main HttpsURLConnectionWitupTest
 */

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        boolean thrown = false;
        String message = null;
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
            message = e.getMessage();
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for null HostnameVerifier");
        }
        if (!"no default HostnameVerifier specified".equals(message)) {
            throw new AssertionError("Unexpected message: " + message);
        }

        HostnameVerifier verifier = (hostname, session) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(verifier);
    }
}
