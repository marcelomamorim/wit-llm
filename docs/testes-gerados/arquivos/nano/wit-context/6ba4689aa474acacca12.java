/* @test    @summary Test for setHostnameVerifier method handling null argument    @run main HttpsURLConnectionWitupTest */

import com.sun.net.ssl.HttpsURLConnection;
import javax.net.ssl.HostnameVerifier;
import java.net.URL;
import java.io.IOException;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetHostnameVerifierWithNull();
        testSetHostnameVerifierWithValid();
    }

    private static void testSetHostnameVerifierWithNull() {
        HttpsURLConnection connection = createConnection();
        try {
            connection.setHostnameVerifier(null);
            System.out.println("FAIL: Expected IllegalArgumentException for null argument");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null argument");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testSetHostnameVerifierWithValid() {
        HttpsURLConnection connection = createConnection();
        HostnameVerifier verifier = new HostnameVerifier() {
            public boolean verify(String hostname, String session) {
                return true;
            }
        };
        try {
            connection.setHostnameVerifier(verifier);
            System.out.println("PASS: setHostnameVerifier with valid verifier succeeded");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static HttpsURLConnection createConnection() {
        try {
            URL url = new URL("https://example.com");
            return (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
