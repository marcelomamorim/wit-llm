/*
 * @test
 * @summary Witup-style tests for CertificatePolicyMap(DerValue) constructor
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificatePolicyMapWitupTest
 */
import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.x509.CertificatePolicyMap;

public class CertificatePolicyMapWitupTest {
    public static void main(String[] args) throws Exception {
        testNonSequenceTagRejected();
        testInvalidInnerTagRejected();
        testValidEncodingAccepted();
    }

    static void testNonSequenceTagRejected() throws Exception {
        DerValue val = new DerValue(new byte[] {0x04, 0x00});
        try {
            new CertificatePolicyMap(val);
            throw new RuntimeException("Expected IOException for non-SEQUENCE tag");
        } catch (IOException expected) {
        }
    }

    static void testInvalidInnerTagRejected() throws Exception {
        DerValue val = new DerValue(new byte[] {0x30, 0x04, 0x04, 0x00, 0x04, 0x00});
        try {
            new CertificatePolicyMap(val);
            throw new RuntimeException("Expected IOException for non-OID inner tag");
        } catch (IOException expected) {
        }
    }

    static void testValidEncodingAccepted() throws Exception {
        DerValue val = new DerValue(new byte[] {
            0x30, 0x0A,
            0x06, 0x03, 0x55, 0x1D, 0x20,
            0x06, 0x03, 0x55, 0x1D, 0x21
        });
        CertificatePolicyMap map = new CertificatePolicyMap(val);
        if (map.getIssuerIdentifier() == null) {
            throw new RuntimeException("Issuer identifier must not be null");
        }
        if (map.getSubjectIdentifier() == null) {
            throw new RuntimeException("Subject identifier must not be null");
        }
        if (!map.toString().contains("CertificatePolicyMap")) {
            throw new RuntimeException("Unexpected toString output");
        }
    }
}
