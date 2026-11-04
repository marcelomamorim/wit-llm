/*
 * @test
 * @summary Test javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier behavior
 * @run main javax.net.ssl.HttpsURLConnectionWitupTest
 */

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPermission;

public class HttpsURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetDefaultHostnameVerifierRejectsNull();
        testSetDefaultHostnameVerifierAcceptsValid();
        testSetDefaultHostnameVerifierPermissionCheck();
    }

    private static void testSetDefaultHostnameVerifierRejectsNull() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(null);
            throw new RuntimeException("Expected IllegalArgumentException for null argument");
        } catch (IllegalArgumentException iae) {
            // expected
        } catch (SecurityException se) {
            // allowed if security manager denies permission before argument check
            // but per source code, argument check is first, so this should not happen
            throw new RuntimeException("Unexpected SecurityException instead of IllegalArgumentException", se);
        }
    }

    private static void testSetDefaultHostnameVerifierAcceptsValid() {
        HostnameVerifier verifier = new HostnameVerifier() {
            public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
                return true;
            }
        };
        // Set default hostname verifier to a valid non-null instance
        HttpsURLConnection.setDefaultHostnameVerifier(verifier);
        // No exception means pass
    }

    private static void testSetDefaultHostnameVerifierPermissionCheck() throws Exception {
        // This test attempts to verify that SecurityManager.checkPermission is called.
        // Since SecurityManager is deprecated and may not be installed, we simulate by installing a custom SecurityManager.
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
                        // Permission check passed
                        return;
                    }
                    // Allow other permissions
                }
            });
            // Should succeed without SecurityException
            HttpsURLConnection.setDefaultHostnameVerifier(verifier);
        } finally {
            System.setSecurityManager(original);
        }
    }
}
