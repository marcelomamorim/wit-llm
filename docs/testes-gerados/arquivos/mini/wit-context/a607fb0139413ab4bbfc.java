/*
 * @test
 * @summary Test LDAPCertStoreParameters(String serverName) constructor for null and valid inputs
 * @run main LDAPCertStoreParametersWitupTest
 */

public class LDAPCertStoreParametersWitupTest {
    public static void main(String[] args) {
        testNullServerNameThrows();
        testValidServerName();
    }

    private static void testNullServerNameThrows() {
        try {
            new java.security.cert.LDAPCertStoreParameters((String) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testValidServerName() {
        String serverName = "ldap.example.com";
        java.security.cert.LDAPCertStoreParameters params = new java.security.cert.LDAPCertStoreParameters(serverName);
        if (!serverName.equals(params.getServerName())) {
            throw new RuntimeException("Server name mismatch: expected " + serverName + ", got " + params.getServerName());
        }
        if (params.getPort() != 389) {
            throw new RuntimeException("Port mismatch: expected 389, got " + params.getPort());
        }
    }
}
