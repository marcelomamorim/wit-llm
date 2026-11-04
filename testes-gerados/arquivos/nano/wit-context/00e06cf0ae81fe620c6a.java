/* @test    @summary Tests for HostnameChecker.getInstance(byte) method    @run main HostnameCheckerWitupTest */

import sun.security.util.HostnameChecker;

public class HostnameCheckerWitupTest {

    public static void main(String[] args) {
        testGetInstanceWithValidTypes();
        testGetInstanceWithInvalidType();
    }

    private static void testGetInstanceWithValidTypes() {
        try {
            HostnameChecker checkerTLS = HostnameChecker.getInstance(HostnameChecker.TYPE_TLS);
            if (checkerTLS == null) {
                throw new AssertionError("Expected non-null HostnameChecker for TYPE_TLS");
            }
            HostnameChecker checkerLDAP = HostnameChecker.getInstance(HostnameChecker.TYPE_LDAP);
            if (checkerLDAP == null) {
                throw new AssertionError("Expected non-null HostnameChecker for TYPE_LDAP");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid types: " + e);
        }
    }

    private static void testGetInstanceWithInvalidType() {
        try {
            HostnameChecker.getInstance((byte) 3);
            throw new AssertionError("Expected IllegalArgumentException for invalid type")
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
