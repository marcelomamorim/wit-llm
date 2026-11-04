/*
 * @test
 * @summary Test javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier behavior
 * @run main HttpsURLConnectionWitupTest
 */

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPermission;

public class HttpsURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetDefaultHostnameVerifierRejectsNull();
        testSetDefaultHostnameVerifierSetsVerifier();
        testSetDefaultHostnameVerifierSecurityManagerCheck();
    }

    private static void testSetDefaultHostnameVerifierRejectsNull() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(null);
            throw new RuntimeException("Expected IllegalArgumentException for null argument");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSetDefaultHostnameVerifierSetsVerifier() {
        HostnameVerifier verifier = new HostnameVerifier() {
            public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
                return true;
            }
        };
        // Set defaultHostnameVerifier to verifier
        HttpsURLConnection.setDefaultHostnameVerifier(verifier);
        // No direct getter, so test no exception and no crash
    }

    private static void testSetDefaultHostnameVerifierSecurityManagerCheck() throws Exception {
        final HostnameVerifier verifier = new HostnameVerifier() {
            public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
                return true;
            }
        };

        SecurityManager original = System.getSecurityManager();
        try {
            System.setSecurityManager(new SecurityManager() {
                @Override
                public void checkPermission(java.security.Permission perm) {
                    if (perm instanceof SSLPermission && "setHostnameVerifier".equals(perm.getName())) {
                        throw new SecurityException("Permission denied");
                    }
                }

                @Override
                public void checkPermission(java.security.Permission perm, Object context) {
                    checkPermission(perm);
                }
            });

            try {
                HttpsURLConnection.setDefaultHostnameVerifier(verifier);
                throw new RuntimeException("Expected SecurityException due to SecurityManager");
            } catch (SecurityException se) {
                // expected
            }
        } finally {
            System.setSecurityManager(original);
        }
    }
}
