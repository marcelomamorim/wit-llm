/*
 * @test
 * @summary Test CertificateIssuerName.get(String) method behavior
 * @run main CertificateIssuerNameWitupTest
 */

import sun.security.x509.CertificateIssuerName;
import sun.security.x509.X500Name;
import javax.security.auth.x500.X500Principal;
import java.io.IOException;

public class CertificateIssuerNameWitupTest {
    public static void main(String[] args) throws Exception {
        testGetDnName();
        testGetDnPrincipalCaching();
        testGetUnrecognizedNameThrows();
    }

    private static void testGetDnName() throws IOException {
        X500Name x500Name = new X500Name("CN=Test, OU=TestUnit");
        CertificateIssuerName issuerName = new CertificateIssuerName(x500Name);

        Object result = issuerName.get("dname");
        if (!(result instanceof X500Name)) {
            throw new RuntimeException("Expected X500Name instance for 'dname'");
        }
        if (!result.equals(x500Name)) {
            throw new RuntimeException("Returned X500Name does not match the original");
        }
    }

    private static void testGetDnPrincipalCaching() throws IOException {
        X500Name x500Name = new X500Name("CN=CacheTest, OU=CacheUnit");
        CertificateIssuerName issuerName = new CertificateIssuerName(x500Name);

        Object firstCall = issuerName.get("x500principal");
        if (!(firstCall instanceof X500Principal)) {
            throw new RuntimeException("Expected X500Principal instance for 'x500principal'");
        }

        Object secondCall = issuerName.get("x500principal");
        if (secondCall != firstCall) {
            throw new RuntimeException("Expected cached X500Principal instance on second call");
        }
    }

    private static void testGetUnrecognizedNameThrows() {
        X500Name x500Name = new X500Name("CN=FailTest");
        CertificateIssuerName issuerName = new CertificateIssuerName(x500Name);

        try {
            issuerName.get("unknownName");
            throw new RuntimeException("Expected IOException for unrecognized attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
