/*
 * @test
 * @summary Test LDAPCertStoreParameters constructor with single String argument
 * @run main LDAPCertStoreParametersWitupTest
 */

public class LDAPCertStoreParametersWitupTest {
    public static void main(String[] args) {
        testValidServerName();
        testNullServerName();
    }

    private static void testValidServerName() {
        String serverName = "ldap.example.com";
        java.security.cert.LDAPCertStoreParameters params = new java.security.cert.LDAPCertStoreParameters(serverName);
        if (!serverName.equals(params.getServerName())) {
            throw new RuntimeException("getServerName() returned unexpected value");
        }
        if (params.getPort() != 389) {
            throw new RuntimeException("Default port should be 389");
        }
        if (!params.toString().contains(serverName)) {
            throw new RuntimeException("toString() should contain serverName");
        }
    }

    private static void testNullServerName() {
        try {
            new java.security.cert.LDAPCertStoreParameters((String) null);
            throw new RuntimeException("Expected NullPointerException for null serverName");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
