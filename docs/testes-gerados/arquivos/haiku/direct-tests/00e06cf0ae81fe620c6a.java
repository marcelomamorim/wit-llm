/*
 * @test
 * @summary Verifies HostnameChecker.getInstance returns cached TLS/LDAP instances and rejects unknown check types
 * @modules java.base/sun.security.util
 * @run main HostnameCheckerWitupTest
 */

import sun.security.util.HostnameChecker;

public class HostnameCheckerWitupTest {

    public static void main(String[] args) throws Exception {
        HostnameChecker tls = HostnameChecker.getInstance(HostnameChecker.TYPE_TLS);
        HostnameChecker ldap = HostnameChecker.getInstance(HostnameChecker.TYPE_LDAP);

        check(tls != null, "TLS instance is null");
        check(ldap != null, "LDAP instance is null");
        check(tls == HostnameChecker.getInstance(HostnameChecker.TYPE_TLS),
              "TLS instance is not the same on repeated calls");
        check(ldap == HostnameChecker.getInstance(HostnameChecker.TYPE_LDAP),
              "LDAP instance is not the same on repeated calls");
        check(tls != ldap, "TLS and LDAP instances must differ");

        expectIllegalArgument((byte) 0);
        expectIllegalArgument((byte) 3);
        expectIllegalArgument((byte) -1);
    }

    static void expectIllegalArgument(byte checkType) {
        try {
            HostnameChecker.getInstance(checkType);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("No IllegalArgumentException for checkType " + checkType);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
