/*
 * @test
 * @summary Test CertificatePolicyMap constructor with DerValue input for valid and invalid encodings
 * @run main CertificatePolicyMapWitupTest
 */

import sun.security.x509.CertificatePolicyMap;
import sun.security.x509.CertificatePolicyId;
import sun.security.util.DerValue;
import java.io.IOException;

public class CertificatePolicyMapWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithInvalidTag();
        testConstructorWithInvalidInnerOID();
        testConstructorWithValidDerValue();
    }

    private static void testConstructorWithInvalidTag() {
        // Create DerValue with invalid tag (not tag_Sequence)
        DerValue invalidTagVal = new DerValue(DerValue.tag_Boolean, new byte[] {1});
        try {
            new CertificatePolicyMap(invalidTagVal);
            throw new RuntimeException("Expected IOException for invalid tag not thrown");
        } catch (IOException e) {
            // Expected exception
            if (!"Invalid encoding for CertificatePolicyMap".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }

    private static void testConstructorWithInvalidInnerOID() throws Exception {
        // Construct a DerValue with tag_Sequence but inner values not valid OIDs
        // We simulate this by creating a DerValue sequence with two invalid DerValues
        // The first inner DerValue will have an invalid tag (not 0x06 for OID)

        // Create invalid inner DerValue with tag 0x01 (BOOLEAN) instead of OID
        DerValue invalidInner = new DerValue(DerValue.tag_Boolean, new byte[] {0});

        // Create a DerOutputStream to hold the sequence
        sun.security.util.DerOutputStream dos = new sun.security.util.DerOutputStream();
        dos.write(invalidInner);
        dos.write(invalidInner);

        byte[] seqBytes = dos.toByteArray();

        DerValue seqVal = new DerValue(DerValue.tag_Sequence, seqBytes);

        try {
            new CertificatePolicyMap(seqVal);
            throw new RuntimeException("Expected IOException for invalid inner OID not thrown");
        } catch (IOException e) {
            // Expected exception due to invalid inner OID tag
        }
    }

    private static void testConstructorWithValidDerValue() throws Exception {
        // Create valid CertificatePolicyId OIDs
        // OID 1.2.3.4 and 1.2.3.5 for issuer and subject
        DerValue oid1 = new DerValue(DerValue.tag_ObjectId, new byte[] {0x2a, 0x03, 0x04}); // 1.2.3.4
        DerValue oid2 = new DerValue(DerValue.tag_ObjectId, new byte[] {0x2a, 0x03, 0x05}); // 1.2.3.5

        sun.security.util.DerOutputStream dos = new sun.security.util.DerOutputStream();
        dos.write(oid1);
        dos.write(oid2);

        DerValue seqVal = new DerValue(DerValue.tag_Sequence, dos.toByteArray());

        CertificatePolicyMap cpm = new CertificatePolicyMap(seqVal);

        if (!cpm.getIssuerIdentifier().toString().contains("1.2.3.4")) {
            throw new RuntimeException("IssuerIdentifier OID mismatch");
        }
        if (!cpm.getSubjectIdentifier().toString().contains("1.2.3.5")) {
            throw new RuntimeException("SubjectIdentifier OID mismatch");
        }
    }
}
