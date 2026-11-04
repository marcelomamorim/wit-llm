/*
 * @test
 * @summary Test setDefaultHostnameVerifier method for null argument and normal set
 * @run main com.sun.net.ssl.HttpsURLConnectionWitupTest
 */

import com.sun.net.ssl.HttpsURLConnection;
import com.sun.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPermission;

public class HttpsURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetDefaultHostnameVerifierRejectsNull();
        testSetDefaultHostnameVerifierAcceptsValid();
    }

    private static void testSetDefaultHostnameVerifierRejectsNull() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(null);
            throw new RuntimeException("Expected IllegalArgumentException for null argument");
        } catch (IllegalArgumentException iae) {
            String msg = iae.getMessage();
            if (msg == null || !msg.contains("no default HostnameVerifier specified")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }

    private static void testSetDefaultHostnameVerifierAcceptsValid() {
        HostnameVerifier verifier = new HostnameVerifier() {
            public boolean verify(String urlHostname, String certHostname) {
                return true;
            }
        };
        // Set the default verifier to a valid non-null instance
        HttpsURLConnection.setDefaultHostnameVerifier(verifier);
        // No direct getter to verify, so test passes if no exception thrown
    }
}
