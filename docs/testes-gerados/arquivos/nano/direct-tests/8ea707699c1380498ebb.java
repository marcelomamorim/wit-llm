/* @test    @summary Test delete method of CertificateIssuerName class    @run main CertificateIssuerNameWitupTest */

import java.io.IOException;
import java.io.ByteArrayInputStream;
import sun.security.x509.CertificateIssuerName;

public class CertificateIssuerNameWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteRecognizedName();
        testDeleteUnrecognizedName();
    }

    private static void testDeleteRecognizedName() throws Exception {
        // Setup: create an instance with a dummy X500Name
        // Since X500Name is not accessible, simulate with null for testing delete
        CertificateIssuerName issuerName = new CertificateIssuerName((sun.security.x509.X500Name) null);
        // Call delete with recognized name
        issuerName.delete("dname");
        // After deletion, dnName and dnPrincipal should be null
        assert issuerName.toString().equals("") : "Expected empty string after delete";
    }

    private static void testDeleteUnrecognizedName() throws Exception {
        // Setup: create an instance
        CertificateIssuerName issuerName = new CertificateIssuerName((sun.security.x509.X500Name) null);
        boolean exceptionThrown = false;
        try {
            // Call delete with unrecognized name
            issuerName.delete("unknown");
        } catch (IOException e) {
            exceptionThrown = true;
            assert e.getMessage().contains("Attribute name not recognized") : "Unexpected exception message";
        }
        assert exceptionThrown : "Expected IOException to be thrown for unrecognized name";
    }
}
