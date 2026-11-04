/*
 * @test
 * @summary Tests for sun.security.x509.CertificateIssuerName.get(String)
 * @run main CertificateIssuerNameWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import sun.security.x509.CertificateIssuerName;
import sun.security.util.DerValue;
import sun.security.util.DerInputStream;
import javax.security.auth.x500.X500Name;

public class CertificateIssuerNameWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithX500PrincipalName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws Exception {
        // Cria um X500Name fictício para teste
        X500Name x500Name = new X500Name("CN=Test, OU=Unit, O=Organization");
        CertificateIssuerName certIssuer = new CertificateIssuerName(x500Name);
        Object result = certIssuer.get("dname");
        if (result == null || !(result instanceof X500Name)) {
            throw new AssertionError("Expected X500Name result for 'dname'");
        }
        if (!result.toString().equals(x500Name.toString())) {
            throw new AssertionError("Mismatch in X500Name string representation");
        }
    }

    private static void testGetWithX500PrincipalName() throws Exception {
        X500Name x500Name = new X500Name("CN=PrincipalTest");
        CertificateIssuerName certIssuer = new CertificateIssuerName(x500Name);
        Object result = certIssuer.get("x500principal");
        if (result == null || !(result instanceof java.security.cert.X500Principal)) {
            throw new AssertionError("Expected X500Principal result for 'x500principal'");
        }
        java.security.cert.X500Principal principal = (java.security.cert.X500Principal) result;
        if (!principal.getName().contains("CN=PrincipalTest")) {
            throw new AssertionError("X500Principal does not contain expected CN");
        }
    }

    private static void testGetWithInvalidName() throws Exception {
        X500Name x500Name = new X500Name("CN=Invalid");
        CertificateIssuerName certIssuer = new CertificateIssuerName(x500Name);
        boolean exceptionThrown = false;
        try {
            certIssuer.get("unknownAttribute");
        } catch (IOException e) {
            exceptionThrown = true;
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IOException for unrecognized attribute name");
        }
    }
}
