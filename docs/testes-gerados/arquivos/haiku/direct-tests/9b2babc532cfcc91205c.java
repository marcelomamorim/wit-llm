/*
 * @test
 * @summary Verify CertificateSerialNumber(DerValue) decodes a DER INTEGER and rejects non-integer tags
 * @modules java.base/sun.security.util
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import sun.security.util.DerValue;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        testRoundTripFromDerValue();
        testDecodedEqualsOriginalEncoding();
        testNonIntegerTagRejected();
    }

    static byte[] encodeOf(CertificateSerialNumber csn) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        csn.encode(baos);
        return baos.toByteArray();
    }

    static void testRoundTripFromDerValue() throws Exception {
        CertificateSerialNumber original = new CertificateSerialNumber(BigInteger.valueOf(1234));
        byte[] der = encodeOf(original);
        CertificateSerialNumber decoded = new CertificateSerialNumber(new DerValue(der));
        if (!original.toString().equals(decoded.toString())) {
            throw new RuntimeException("toString mismatch: " + original + " vs " + decoded);
        }
    }

    static void testDecodedEqualsOriginalEncoding() throws Exception {
        CertificateSerialNumber original = new CertificateSerialNumber(BigInteger.valueOf(42));
        byte[] der = encodeOf(original);
        CertificateSerialNumber decoded = new CertificateSerialNumber(new DerValue(der));
        byte[] reencoded = encodeOf(decoded);
        if (!java.util.Arrays.equals(der, reencoded)) {
            throw new RuntimeException("re-encoding differs from original DER");
        }
    }

    static void testNonIntegerTagRejected() throws Exception {
        // DER OCTET STRING (tag 0x04), length 1, value 0x05: not an INTEGER
        byte[] notInteger = new byte[] {0x04, 0x01, 0x05};
        boolean threw = false;
        try {
            new CertificateSerialNumber(new DerValue(notInteger));
        } catch (IOException expected) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("expected IOException for non-INTEGER DerValue");
        }
    }
}
