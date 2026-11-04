/* @test    @summary Test for CertificateIssuerName.delete(String) method to verify exception handling and behavior.    @run main CertificateIssuerNameWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateIssuerName;

public class CertificateIssuerNameWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithDname();
        testDeleteWithOtherName();
    }

    private static void testDeleteWithDname() throws Exception {
        // Setup: create a CertificateIssuerName with a valid X500Name
        X500Name name = new X500Name("CN=Test, OU=Unit, O=Organization");
        CertificateIssuerName certIssuerName = new CertificateIssuerName(name);
        // Call delete with "dname" (case-insensitive)
        certIssuerName.delete("dname");
        // After deletion, dnName should be null
        if (certIssuerName.toString().isEmpty()) {
            System.out.println("testDeleteWithDname passed");
        } else {
            throw new AssertionError("Expected empty string after delete dname");
        }
    }

    private static void testDeleteWithOtherName() {
        // Setup: create a CertificateIssuerName with a valid X500Name
        X500Name name = new X500Name("CN=Test, OU=Unit, O=Organization");
        CertificateIssuerName certIssuerName = new CertificateIssuerName(name);
        // Call delete with a different name, expect IOException
        try {
            certIssuerName.delete("othername");
            throw new AssertionError("Expected IOException for unrecognized attribute name");
        } catch (IOException e) {
            String expectedMessage = "Attribute name not recognized by CertAttrSet:CertificateIssuerName.";
            if (e.getMessage().equals(expectedMessage)) {
                System.out.println("testDeleteWithOtherName passed");
            } else {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
