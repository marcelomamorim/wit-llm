/*
 * @test
 * @summary Verify HostnameChecker.getInstance returns the TLS and LDAP instances and rejects unknown check types
 * @modules java.base/sun.security.util
 * @run main HostnameCheckerWitupTest
 */

import sun.security.util.HostnameChecker;

public class HostnameCheckerWitupTest {

    public static void main(String[] args) throws Exception {
        HostnameChecker tls = HostnameChecker.getInstance(HostnameChecker.TYPE_TLS);
        HostnameChecker ldap = HostnameChecker.getInstance(HostnameChecker.TYPE_LDAP);

        if (tls == null || ldap == null) {
            throw new RuntimeException("getInstance returned null");
        }
        if (tls != HostnameChecker.getInstance(HostnameChecker.TYPE_TLS)) {
            throw new RuntimeException("TLS instance is not stable across calls");
        }
        if (ldap != HostnameChecker.getInstance(HostnameChecker.TYPE_LDAP)) {
            throw new RuntimeException("LDAP instance is not stable across calls");
        }
        if (tls == ldap) {
            throw new RuntimeException("TLS and LDAP instances must differ");
        }

        checkUnknown((byte) 0);
        checkUnknown((byte) 3);
        checkUnknown((byte) -1);
    }

    private static void checkUnknown(byte type) {
        try {
            HostnameChecker.getInstance(type);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for check type " + type);
    }
}
