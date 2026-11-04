/*
 * @test
 * @summary WITUP-derived checks for CertificateSerialNumber(DerValue) on valid INTEGER and non-INTEGER tags
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificateSerialNumberWitupTest
 */
import java.io.IOException;
import java.math.BigInteger;

import sun.security.util.DerValue;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        testValidIntegerDecodesToSameSerial();
        testNonIntegerTagIsRejected();
    }

    static void testValidIntegerDecodesToSameSerial() throws IOException {
        DerValue val = new DerValue(new byte[] {0x02, 0x01, 0x05});
        CertificateSerialNumber fromDer = new CertificateSerialNumber(val);
        CertificateSerialNumber expected = new CertificateSerialNumber(BigInteger.valueOf(5));
        if (fromDer.toString() == null || fromDer.toString().isEmpty()) {
            throw new RuntimeException("toString() must not be empty for a decoded serial");
        }
        if (!fromDer.toString().equals(expected.toString())) {
            throw new RuntimeException("Decoded serial differs from BigInteger.valueOf(5): "
                    + fromDer.toString() + " vs " + expected.toString());
        }
    }

    static void testNonIntegerTagIsRejected() {
        boolean threw = false;
        try {
            DerValue val = new DerValue(new byte[] {0x04, 0x01, 0x05});
            new CertificateSerialNumber(val);
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException for non-INTEGER DER tag");
        }
    }
}
