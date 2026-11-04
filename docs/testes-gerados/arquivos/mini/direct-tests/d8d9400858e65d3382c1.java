/*
 * @test
 * @summary Test CertificatePolicyMap constructor with DerValue input
 * @run main CertificatePolicyMapWitupTest
 */

import sun.security.x509.CertificatePolicyMap;
import sun.security.x509.CertificatePolicyId;
import sun.security.util.DerValue;
import sun.security.util.DerInputStream;
import java.io.IOException;

public class CertificatePolicyMapWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testInvalidTagThrowsIOException();
    }

    private static void testValidDerValue() throws Exception {
        // Construct a valid DER encoding for CertificatePolicyMap
        // CertificatePolicyMap ::= SEQUENCE { issuerDomain CertificatePolicyId, subjectDomain CertificatePolicyId }
        // CertificatePolicyId ::= OID
        // We create two CertificatePolicyId DER encodings (OID 1.2.3.4 and 1.2.3.5)

        byte[] oid1 = new byte[] {0x06, 0x03, 0x2a, 0x03, 0x04}; // OID 1.2.3.4
        byte[] oid2 = new byte[] {0x06, 0x03, 0x2a, 0x03, 0x05}; // OID 1.2.3.5

        DerOutputStream tmp = new DerOutputStream();
        tmp.write(oid1);
        tmp.write(oid2);

        DerOutputStream seq = new DerOutputStream();
        seq.write(DerValue.tag_Sequence, tmp);

        byte[] encoded = seq.toByteArray();

        DerValue val = new DerValue(encoded);

        CertificatePolicyMap map = new CertificatePolicyMap(val);

        CertificatePolicyId issuer = map.getIssuerIdentifier();
        CertificatePolicyId subject = map.getSubjectIdentifier();

        if (!issuer.toString().contains("1.2.3.4")) {
            throw new RuntimeException("Issuer CertificatePolicyId OID mismatch");
        }
        if (!subject.toString().contains("1.2.3.5")) {
            throw new RuntimeException("Subject CertificatePolicyId OID mismatch");
        }

        String str = map.toString();
        if (!str.contains("IssuerDomain") || !str.contains("SubjectDomain")) {
            throw new RuntimeException("toString() output missing expected labels");
        }
    }

    private static void testInvalidTagThrowsIOException() {
        // Create a DerValue with invalid tag (not SEQUENCE)
        try {
            DerValue val = new DerValue(DerValue.tag_Boolean, new byte[] {0x01});
            new CertificatePolicyMap(val);
            throw new RuntimeException("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"Invalid encoding for CertificatePolicyMap".equals(msg)) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
