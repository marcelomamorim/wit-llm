/*
 * @test
 * @summary Verifies LDAPCertStoreParameters single-argument constructor: server name is kept and null server name throws NullPointerException
 * @run main LDAPCertStoreParametersWitupTest
 */

import java.security.cert.LDAPCertStoreParameters;

public class LDAPCertStoreParametersWitupTest {

    public static void main(String[] args) throws Exception {
        LDAPCertStoreParameters params = new LDAPCertStoreParameters("ldap.example.com");
        if (!"ldap.example.com".equals(params.getServerName())) {
            throw new RuntimeException("Unexpected server name: " + params.getServerName());
        }

        boolean thrown = false;
        try {
            new LDAPCertStoreParameters((String) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null serverName");
        }
    }
}
