/* @test    @summary Test for CertificateSubjectName.delete(String) method to verify exception handling and behavior.    @run main CertificateSubjectNameWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateSubjectName;

public class CertificateSubjectNameWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithDname();
        testDeleteWithOtherName();
    }

    private static void testDeleteWithDname() throws Exception {
        // Setup: create a CertificateSubjectName with a valid X500Name
        X500Name name = new X500Name("CN=Test, OU=Unit, O=Organization");
        CertificateSubjectName csn = new CertificateSubjectName(name);
        // Call delete with "dname" (case-insensitive)
        csn.delete("dname");
        // After deletion, dnName should be null
        if (csn.toString().isEmpty()) {
            System.out.println("testDeleteWithDname passed");
        } else {
            throw new AssertionError("Expected empty string after delete dname");
        }
    }

    private static void testDeleteWithOtherName() {
        // Setup: create a CertificateSubjectName with a valid X500Name
        X500Name name = new X500Name("CN=Test, OU=Unit, O=Organization");
        CertificateSubjectName csn;
        try {
            csn = new CertificateSubjectName(name);
            // Call delete with a different name
            csn.delete("othername");
            throw new AssertionError("Expected IOException for unrecognized attribute name");
        } catch (IOException e) {
            // Expected exception
            if (e.getMessage().contains("Attribute name not recognized")) {
                System.out.println("testDeleteWithOtherName passed");
            } else {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
