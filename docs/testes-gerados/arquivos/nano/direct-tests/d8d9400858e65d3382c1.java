/* @test    @summary Test for CertificatePolicyMap constructor with DerValue input    @run main CertificatePolicyMapWitupTest */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.x509.CertificatePolicyMap;
import sun.security.x509.CertificatePolicyId;

public class CertificatePolicyMapWitupTest {

    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testInvalidDerValueTag();
    }

    private static void testValidDerValue() throws IOException {
        // Create valid DerValues for issuer and subject
        DerValue issuerDer = new DerValue(new byte[] {0x30, 0x03, 0x02, 0x01, 0x01}); // Sequence with integer 1
        DerValue subjectDer = new DerValue(new byte[] {0x30, 0x03, 0x02, 0x01, 0x02}); // Sequence with integer 2
        DerValue[] sequence = {issuerDer, subjectDer};
        DerValue sequenceDer = new DerValue(DerValue.tag_Sequence, sequence);
        // Instantiate CertificatePolicyMap with valid DerValue
        CertificatePolicyMap cpm = new CertificatePolicyMap(sequenceDer);
        assert cpm.getIssuerIdentifier() != null : "IssuerIdentifier should not be null";
        assert cpm.getSubjectIdentifier() != null : "SubjectIdentifier should not be null";
        System.out.println("testValidDerValue passed");
    }

    private static void testInvalidDerValueTag() {
        // Create DerValue with invalid tag
        DerValue invalidDer = new DerValue((byte)0x05, new byte[] {0x00}); // NULL tag
        try {
            new CertificatePolicyMap(invalidDer);
            assert false : "Expected IOException for invalid tag";
        } catch (IOException e) {
            assert e.getMessage().contains("Invalid encoding") : "Unexpected exception message";
            System.out.println("testInvalidDerValueTag passed");
        }
    }
}
