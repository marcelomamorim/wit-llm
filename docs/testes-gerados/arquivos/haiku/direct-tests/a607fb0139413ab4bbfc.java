/*
 * @test
 * @summary Verify LDAPCertStoreParameters(String) uses default port 389 and rejects null server name
 * @run main LDAPCertStoreParametersWitupTest
 */

import java.security.cert.LDAPCertStoreParameters;

public class LDAPCertStoreParametersWitupTest {

    public static void main(String[] args) {
        LDAPCertStoreParameters p = new LDAPCertStoreParameters("ldap.example.com");
        check("ldap.example.com".equals(p.getServerName()),
              "server name mismatch: " + p.getServerName());
        check(p.getPort() == 389,
              "default port must be 389, was " + p.getPort());

        LDAPCertStoreParameters q = new LDAPCertStoreParameters("ldap.example.com", 1636);
        check(q.getPort() == 1636,
              "explicit port must be 1636, was " + q.getPort());

        boolean thrown = false;
        try {
            new LDAPCertStoreParameters((String) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null serverName must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
