/*
 * @test
 * @summary Test sun.security.x509.CertificateIssuerName.get(String) method
 * @run main CertificateIssuerNameWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.X500Name;

public class CertificateIssuerNameWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithDNName();
        testGetWithDNPrincipal();
        testGetWithInvalidName();
    }

    private static void testGetWithDNName() throws IOException {
        X500Name name = new X500Name(); // X500Name() public constructor exists in JDK 11
        CertificateIssuerName issuerName = new CertificateIssuerName(name);
        Object result = issuerName.get("dname");
        if (result == null || !(result instanceof X500Name)) {
            throw new RuntimeException("Expected X500Name instance for 'dname'");
        }
    }

    private static void testGetWithDNPrincipal() throws IOException {
        X500Name name = new X500Name();
        CertificateIssuerName issuerName = new CertificateIssuerName(name);
        Object result = issuerName.get("x500principal");
        if (result == null) {
            throw new RuntimeException("Expected non-null X500Principal for 'x500principal'");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            X500Name name = new X500Name();
            CertificateIssuerName issuerName = new CertificateIssuerName(name);
            issuerName.get("invalidName");
            throw new RuntimeException("Expected IOException for unrecognized attribute name");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
