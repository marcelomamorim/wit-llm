/*
 * @test
 * @summary Test constructor LDAPCertStoreParameters(String serverName, int port) for null and valid inputs
 * @run main LDAPCertStoreParametersWitupTest
 */

public class LDAPCertStoreParametersWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidParameters();
        testConstructorWithNullServerName();
    }

    private static void testConstructorWithValidParameters() {
        String serverName = "ldap.example.com";
        int port = 1234;
        java.security.cert.LDAPCertStoreParameters params = new java.security.cert.LDAPCertStoreParameters(serverName, port);
        if (!serverName.equals(params.getServerName())) {
            throw new RuntimeException("getServerName() returned unexpected value");
        }
        if (params.getPort() != port) {
            throw new RuntimeException("getPort() returned unexpected value");
        }
    }

    private static void testConstructorWithNullServerName() {
        try {
            new java.security.cert.LDAPCertStoreParameters(null, 389);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
