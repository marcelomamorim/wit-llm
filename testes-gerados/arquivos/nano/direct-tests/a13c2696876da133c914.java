/* @test    @summary Test for CertificateIssuerName.get(String) method behavior.    @run main CertificateIssuerNameWitupTest */

import java.io.IOException;
import java.io.ByteArrayInputStream;
import sun.security.x509.CertificateIssuerName;
import sun.security.util.X500Name;

public class CertificateIssuerNameWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithDname();
        testGetWithDnamePrincipal();
        testGetWithInvalidName();
    }

    static void testGetWithDname() throws Exception {
        X500Name name = new X500Name("CN=Test, OU=Unit, O=Organization, C=US");
        CertificateIssuerName certIssuer = new CertificateIssuerName(name);
        Object result = certIssuer.get("dname");
        if (result == null || !(result instanceof X500Name)) {
            throw new AssertionError("Expected X500Name for 'dname', got: " + result);
        }
        if (!result.equals(name)) {
            throw new AssertionError("Returned X500Name does not match the original.");
        }
    }

    static void testGetWithDnamePrincipal() throws Exception {
        X500Name name = new X500Name("CN=PrincipalTest, OU=Unit, O=Organization, C=US");
        CertificateIssuerName certIssuer = new CertificateIssuerName(name);
        Object result = certIssuer.get("x500principal");
        if (result == null || !(result instanceof java.security.cert.X500Principal)) {
            throw new AssertionError("Expected X500Principal for 'x500principal', got: " + result);
        }
        java.security.cert.X500Principal principal = (java.security.cert.X500Principal) result;
        if (!principal.getName().equals(name.asX500Principal().getName())) {
            throw new AssertionError("X500Principal name does not match the original X500Name.");
        }
    }

    static void testGetWithInvalidName() {
        X500Name name = new X500Name("CN=Invalid, OU=Unit");
        CertificateIssuerName certIssuer;
        try {
            certIssuer = new CertificateIssuerName(name);
        } catch (IOException e) {
            throw new AssertionError("Failed to create CertificateIssuerName: " + e);
        }
        try {
            certIssuer.get("invalidName");
            throw new AssertionError("Expected IOException for unrecognized attribute name.");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
