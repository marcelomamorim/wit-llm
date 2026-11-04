/*
 * @test
 * @summary Test for LDAPCertStoreParameters constructor with serverName
 * @run main LDAPCertStoreParametersWitupTest
 */

import java.security.cert.LDAPCertStoreParameters;

public class LDAPCertStoreParametersWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidServerName();
        testConstructorWithNullServerName();
    }

    private static void testConstructorWithValidServerName() {
        String serverName = "example.com";
        LDAPCertStoreParameters params = new LDAPCertStoreParameters(serverName);
        assert params.getServerName().equals(serverName) : "Server name should match input";
    }

    private static void testConstructorWithNullServerName() {
        try {
            new LDAPCertStoreParameters((String) null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
