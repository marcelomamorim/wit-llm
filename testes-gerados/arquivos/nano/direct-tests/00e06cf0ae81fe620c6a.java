/* @test    @summary Test HostnameChecker.getInstance() method for valid and invalid check types    @run main HostnameCheckerWitupTest */

import sun.security.util.HostnameChecker;

public class HostnameCheckerWitupTest {

    public static void main(String[] args) {
        testGetInstanceWithValidTypes();
        testGetInstanceWithInvalidType();
    }

    private static void testGetInstanceWithValidTypes() {
        HostnameChecker checkerTLS = HostnameChecker.getInstance(HostnameChecker.TYPE_TLS);
        assert checkerTLS != null : "Expected non-null for TYPE_TLS";
        HostnameChecker checkerLDAP = HostnameChecker.getInstance(HostnameChecker.TYPE_LDAP);
        assert checkerLDAP != null : "Expected non-null for TYPE_LDAP";
    }

    private static void testGetInstanceWithInvalidType() {
        boolean exceptionThrown = false;
        try {
            HostnameChecker.getInstance((byte) 99);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            assert e.getMessage().contains("Unknown check type") : "Unexpected exception message: " + e.getMessage();
        }
        assert exceptionThrown : "Expected IllegalArgumentException for invalid check type";
    }
}
