/*
 * @test
 * @summary Test CertificateIssuerName.delete(String) method behavior
 * @run main CertificateIssuerNameWitupTest
 */

import sun.security.x509.CertificateIssuerName;
import sun.security.x509.X500Name;
import java.io.IOException;

public class CertificateIssuerNameWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        X500Name x500Name = new X500Name("CN=Test, OU=TestUnit, O=TestOrg, L=TestCity, ST=TestState, C=US");
        CertificateIssuerName issuerName = new CertificateIssuerName(x500Name);

        // Before delete, toString should return non-empty string
        String beforeDelete = issuerName.toString();
        if (beforeDelete == null || beforeDelete.isEmpty()) {
            throw new RuntimeException("toString() returned empty before delete");
        }

        // Delete with DN_NAME should clear dnName and dnPrincipal
        issuerName.delete(CertificateIssuerName.DN_NAME);

        // After delete, toString should return empty string
        String afterDelete = issuerName.toString();
        if (!"".equals(afterDelete)) {
            throw new RuntimeException("toString() did not return empty string after delete");
        }
    }

    private static void testDeleteWithInvalidName() {
        X500Name x500Name = new X500Name("CN=Test");
        CertificateIssuerName issuerName = new CertificateIssuerName(x500Name);

        try {
            issuerName.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
