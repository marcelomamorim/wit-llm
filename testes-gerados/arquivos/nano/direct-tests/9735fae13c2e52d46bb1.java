/* @test    @summary Test for setDefaultHostnameVerifier method in HttpsURLConnection    @run main HttpsURLConnectionWitupTest */

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.security.Security;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetDefaultHostnameVerifierWithValidVerifier();
        testSetDefaultHostnameVerifierWithNullVerifier();
    }

    private static void testSetDefaultHostnameVerifierWithValidVerifier() {
        HostnameVerifier verifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
                return true;
            }
        };
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(verifier);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid verifier", e);
        }
    }

    private static void testSetDefaultHostnameVerifierWithNullVerifier() {
        boolean exceptionThrown = false;
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for null verifier")
        }
    }
}
