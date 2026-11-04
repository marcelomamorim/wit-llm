/*
 * @test
 * @summary Verifies argument validation and SecurityManager checks of HttpsURLConnection.setDefaultHostnameVerifier
 * @run main HttpsURLConnectionWitupTest
 */

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPermission;
import java.security.Permission;

public class HttpsURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testNullVerifierRejected();
        testValidVerifierAcceptedWithoutSecurityManager();
        testDeniedBySecurityManager();
        testAllowedBySecurityManager();
        System.setSecurityManager(null);
    }

    static void testNullVerifierRejected() {
        boolean thrown = false;
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IllegalArgumentException expected for null verifier");
        }
    }

    static void testValidVerifierAcceptedWithoutSecurityManager() {
        HostnameVerifier hv = (h, s) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    static void testDeniedBySecurityManager() {
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                if (perm instanceof SSLPermission
                        && "setHostnameVerifier".equals(perm.getName())) {
                    throw new SecurityException("denied");
                }
            }
        });
        HostnameVerifier hv = (h, s) -> false;
        boolean denied = false;
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
        } catch (SecurityException e) {
            denied = true;
        }
        if (!denied) {
            throw new RuntimeException("SecurityException expected when permission is denied");
        }
    }

    static void testAllowedBySecurityManager() {
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
            }
        });
        HostnameVerifier hv = (h, s) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }
}
