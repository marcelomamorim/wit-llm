/*
 * @test
 * @summary Test com.sun.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier behavior
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
        } catch (IllegalArgumentException e) {
            if (!"no default HostnameVerifier specified".equals(e.getMessage())) {
                throw new RuntimeException("IllegalArgumentException message mismatch", e);
            }
        }
    }

    private static void testSetDefaultHostnameVerifierAcceptsValid() {
        HostnameVerifier verifier = new HostnameVerifier() {
            public boolean verify(String urlHostname, String certHostname) {
                return true;
            }
        };

        // Set default verifier to a valid non-null instance
        HttpsURLConnection.setDefaultHostnameVerifier(verifier);

        // No direct getter, so test by setting again to another instance to ensure no exception
        HostnameVerifier anotherVerifier = new HostnameVerifier() {
            public boolean verify(String urlHostname, String certHostname) {
                return false;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(anotherVerifier);
    }
}
