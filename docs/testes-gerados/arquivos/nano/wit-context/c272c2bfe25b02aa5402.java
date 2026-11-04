/* @test    @summary Test setDefaultHostnameVerifier with null argument to verify IllegalArgumentException is thrown    @run main HttpsURLConnectionWitupTest */

import com.sun.net.ssl.HttpsURLConnection;
import javax.net.ssl.HostnameVerifier;

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
            System.out.println("Passed: IllegalArgumentException was thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
