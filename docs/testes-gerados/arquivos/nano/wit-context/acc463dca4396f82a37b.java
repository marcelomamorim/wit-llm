/*
 * @test
 * @summary Testa o construtor de LDAPCertStoreParameters com entradas nulas.
 * @run main LDAPCertStoreParametersWitupTest
 */

import java.security.cert.LDAPCertStoreParameters;

public class LDAPCertStoreParametersWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullServerName();
        testConstructorWithValidServerNameAndPort();
        testDefaultConstructor();
    }

    private static void testConstructorWithNullServerName() {
        boolean exceptionThrown = false;
        try {
            new LDAPCertStoreParameters(null, 389);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null serverName");
        }
    }

    private static void testConstructorWithValidServerNameAndPort() {
        String serverName = "example.com";
        int port = 1234;
        LDAPCertStoreParameters params = new LDAPCertStoreParameters(serverName, port);
        if (!params.getServerName().equals(serverName)) {
            throw new AssertionError("Server name mismatch");
        }
        if (params.getServerName() == null) {
            throw new AssertionError("Server name should not be null");
        }
    }

    private static void testDefaultConstructor() {
        LDAPCertStoreParameters params = new LDAPCertStoreParameters();
        if (!params.getServerName().equals("localhost")) {
            throw new AssertionError("Default server name should be 'localhost'");
        }
    }
}
