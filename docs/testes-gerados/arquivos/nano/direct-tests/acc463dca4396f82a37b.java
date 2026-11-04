/* @test    @summary Test LDAPCertStoreParameters constructor with valid serverName and port    @run main LDAPCertStoreParametersWitupTest */

import java.security.cert.LDAPCertStoreParameters;

public class LDAPCertStoreParametersWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidParameters();
        testConstructorWithNullServerName();
        testDefaultConstructor();
    }

    private static void testConstructorWithValidParameters() {
        String serverName = "ldap.example.com";
        int port = 1234;
        LDAPCertStoreParameters params = new LDAPCertStoreParameters(serverName, port);
        assert params.getServerName().equals(serverName) : "Server name should match input";
        assert params.getPort() == port : "Port should match input";
    }

    private static void testConstructorWithNullServerName() {
        boolean exceptionThrown = false;
        try {
            new LDAPCertStoreParameters(null, 389);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "NullPointerException should be thrown when serverName is null";
    }

    private static void testDefaultConstructor() {
        LDAPCertStoreParameters params = new LDAPCertStoreParameters();
        assert params.getServerName().equals("localhost") : "Default server name should be 'localhost'";
        assert params.getPort() == 389 : "Default port should be 389";
    }
}
