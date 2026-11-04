/*
 * @test
 * @summary WITUP-derived behavioral tests for LDAPCertStoreParameters constructors
 * @run main LDAPCertStoreParametersWitupTest
 */

import java.security.cert.LDAPCertStoreParameters;

public class LDAPCertStoreParametersWitupTest {

    public static void main(String[] args) {
        testNullServerNameWithPortThrowsNpe();
        testNullServerNameWithDefaultPortThrowsNpe();
        testServerNameIsStored();
        testDefaultConstructorUsesLocalhost();
    }

    private static void testNullServerNameWithPortThrowsNpe() {
        boolean thrown = false;
        try {
            new LDAPCertStoreParameters(null, 389);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null serverName");
        }
    }

    private static void testNullServerNameWithDefaultPortThrowsNpe() {
        boolean thrown = false;
        try {
            new LDAPCertStoreParameters((String) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null serverName (single-arg constructor)");
        }
    }

    private static void testServerNameIsStored() {
        LDAPCertStoreParameters p = new LDAPCertStoreParameters("ldap.example.com", 636);
        if (!"ldap.example.com".equals(p.getServerName())) {
            throw new RuntimeException("Unexpected serverName: " + p.getServerName());
        }
    }

    private static void testDefaultConstructorUsesLocalhost() {
        LDAPCertStoreParameters p = new LDAPCertStoreParameters();
        if (!"localhost".equals(p.getServerName())) {
            throw new RuntimeException("Unexpected default serverName: " + p.getServerName());
        }
    }
}
