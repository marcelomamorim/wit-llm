/* @test    @summary Test for setHostnameVerifier method in HttpsURLConnection    @run main HttpsURLConnectionWitupTest */

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetHostnameVerifierWithNull();
        testSetHostnameVerifierWithValid();
    }

    private static void testSetHostnameVerifierWithNull() {
        HttpsURLConnection connection = null;
        try {
            // Create a dummy URL object for instantiation
            java.net.URL url = new java.net.URL("https://example.com");
            connection = new HttpsURLConnection(url) {};
        } catch (Exception e) {
            // Should not happen, as we're creating an anonymous subclass
        }
        try {
            connection.setHostnameVerifier(null);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException for null verifier, but none thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Caught expected IllegalArgumentException for null verifier.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }

    private static void testSetHostnameVerifierWithValid() {
        HttpsURLConnection connection = null;
        try {
            java.net.URL url = new java.net.URL("https://example.com");
            connection = new HttpsURLConnection(url) {};
        } catch (Exception e) {
            // Should not happen
        }
        HostnameVerifier verifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
                return true;
            }
        };
        try {
            connection.setHostnameVerifier(verifier);
            System.out.println("setHostnameVerifier with valid verifier succeeded.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
