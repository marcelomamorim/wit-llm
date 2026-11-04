/* @test    @summary Test LDAPCertStoreParameters constructor with serverName only    @run main LDAPCertStoreParametersWitupTest */

import java.security.cert.LDAPCertStoreParameters;

public class LDAPCertStoreParametersWitupTest {

    public static void main(String[] args) {
        testConstructorWithServerName();
        testConstructorWithNullServerName();
    }

    private static void testConstructorWithServerName() {
        String serverName = "example.com";
        LDAPCertStoreParameters params = new LDAPCertStoreParameters(serverName);
        assert params.getServerName().equals(serverName) : "Server name should match input";
    }

    private static void testConstructorWithNullServerName() {
        try {
            new LDAPCertStoreParameters(null);
            assert false : "NullPointerException expected";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
