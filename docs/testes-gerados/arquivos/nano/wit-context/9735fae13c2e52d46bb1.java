/* @test    @summary Test for HttpsURLConnection.setDefaultHostnameVerifier with null argument to verify IllegalArgumentException is thrown.    @run main HttpsURLConnectionWitupTest */

import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetDefaultHostnameVerifierNull();
    }

    public static void testSetDefaultHostnameVerifierNull() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(null);
            System.err.println("Failed: Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
            System.out.println("Passed: IllegalArgumentException thrown as expected.");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
            System.exit(1);
        }
    }
}
