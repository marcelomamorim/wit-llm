/*
 * @test
 * @summary Test constructor LDAPCertStoreParameters(String serverName, int port) for valid and null serverName
 * @run main LDAPCertStoreParametersWitupTest
 */

public class LDAPCertStoreParametersWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testNullServerNameThrows();
    }

    private static void testValidConstructor() {
        java.security.cert.LDAPCertStoreParameters params = new java.security.cert.LDAPCertStoreParameters("ldap.example.com", 1234);
        if (!"ldap.example.com".equals(params.getServerName())) {
            throw new RuntimeException("Expected serverName 'ldap.example.com' but got: " + params.getServerName());
        }
        if (params.getPort() != 1234) {
            throw new RuntimeException("Expected port 1234 but got: " + params.getPort());
        }
    }

    private static void testNullServerNameThrows() {
        try {
            new java.security.cert.LDAPCertStoreParameters(null, 389);
            throw new RuntimeException("Expected NullPointerException for null serverName");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
