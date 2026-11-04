/*
 * @test
 * @summary Tests the LDAPCertStoreParameters(String serverName, int port) constructor
 * @run main LDAPCertStoreParametersWitupTest
 */

import java.security.cert.LDAPCertStoreParameters;

public class LDAPCertStoreParametersWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    public static void main(String[] args) {
        LDAPCertStoreParameters p = new LDAPCertStoreParameters("ldap.example.com", 1389);
        check("ldap.example.com".equals(p.getServerName()),
              "serverName should be stored and returned");
        check(p.getPort() == 1389,
              "port should be stored and returned");

        LDAPCertStoreParameters empty = new LDAPCertStoreParameters("", 0);
        check("".equals(empty.getServerName()),
              "empty serverName should be accepted and returned");
        check(empty.getPort() == 0,
              "port 0 should be stored as given");

        LDAPCertStoreParameters negative = new LDAPCertStoreParameters("host", -1);
        check(negative.getPort() == -1,
              "negative port should be stored as given by the constructor");

        try {
            new LDAPCertStoreParameters(null, 389);
            check(false, "null serverName should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }

        try {
            new LDAPCertStoreParameters(null, 0);
            check(false, "null serverName with port 0 should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
