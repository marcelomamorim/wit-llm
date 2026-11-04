/*
 * @test
 * @summary Tests the DER-decoding constructor CertificatePolicyMap(DerValue)
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificatePolicyMapWitupTest
 */

import java.io.IOException;

import sun.security.util.DerValue;
import sun.security.x509.CertificatePolicyId;
import sun.security.x509.CertificatePolicyMap;

public class CertificatePolicyMapWitupTest {

    public static void main(String[] args) throws Exception {
        testNonSequenceTagThrowsIOException();
        testValidSequenceDecodesBothIds();
        System.out.println("PASSED");
    }

    static void testNonSequenceTagThrowsIOException() throws Exception {
        // INTEGER (tag 0x02) with value 0 instead of SEQUENCE (0x30)
        byte[] encoded = {0x02, 0x01, 0x00};
        DerValue val = new DerValue(encoded);
        boolean thrown = false;
        try {
            new CertificatePolicyMap(val);
        } catch (IOException e) {
            thrown = true;
            if (!e.getMessage().contains("Invalid encoding")) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for non-SEQUENCE tag");
        }
    }

    static void testValidSequenceDecodesBothIds() throws Exception {
        // SEQUENCE { OID 1.2.3, OID 1.2.4 }
        byte[] encoded = {
            0x30, 0x08,
            0x06, 0x02, 0x2A, 0x03,
            0x06, 0x02, 0x2A, 0x04
        };
        DerValue val = new DerValue(encoded);
        CertificatePolicyMap map = new CertificatePolicyMap(val);

        CertificatePolicyId issuer = map.getIssuerIdentifier();
        CertificatePolicyId subject = map.getSubjectIdentifier();
        if (issuer == null || subject == null) {
            throw new RuntimeException("Issuer or subject identifier is null");
        }
        if (issuer == subject) {
            throw new RuntimeException("Issuer and subject should be distinct objects");
        }
        String s = map.toString();
        if (!s.contains("IssuerDomain") || !s.contains("SubjectDomain")) {
            throw new RuntimeException("Unexpected toString(): " + s);
        }
    }
}
