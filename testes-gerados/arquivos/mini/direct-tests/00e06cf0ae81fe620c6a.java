/*
 * @test
 * @summary Test HostnameChecker.getInstance(byte) for valid and invalid inputs
 * @run main HostnameCheckerWitupTest
 */

import sun.security.util.HostnameChecker;

public class HostnameCheckerWitupTest {
    public static void main(String[] args) {
        // Test valid TYPE_TLS
        HostnameChecker tlsChecker = HostnameChecker.getInstance(HostnameChecker.TYPE_TLS);
        if (tlsChecker == null) {
            throw new RuntimeException("getInstance(TYPE_TLS) returned null");
        }

        // Test valid TYPE_LDAP
        HostnameChecker ldapChecker = HostnameChecker.getInstance(HostnameChecker.TYPE_LDAP);
        if (ldapChecker == null) {
            throw new RuntimeException("getInstance(TYPE_LDAP) returned null");
        }

        // Test that TYPE_TLS and TYPE_LDAP return distinct instances
        if (tlsChecker == ldapChecker) {
            throw new RuntimeException("getInstance(TYPE_TLS) and getInstance(TYPE_LDAP) returned the same instance");
        }

        // Test invalid type throws IllegalArgumentException
        boolean threw = false;
        try {
            HostnameChecker.getInstance((byte)0);
        } catch (IllegalArgumentException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Unknown check type")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for invalid checkType");
        }
    }
}
