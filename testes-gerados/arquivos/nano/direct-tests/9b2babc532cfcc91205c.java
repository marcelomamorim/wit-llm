/* @test    @summary Test constructor CertificateSerialNumber(DerValue) for sun.security.x509.CertificateSerialNumber    @run main CertificateSerialNumberWitupTest */

import java.io.IOException;
import java.io.ByteArrayInputStream;
import sun.security.util.DerValue;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws IOException {
        testValidDerValue();
        testInvalidDerValue();
    }

    private static void testValidDerValue() throws IOException {
        byte[] derBytes = {0x02, 0x01, 0x01}; // DER encoding for integer 1
        DerValue derValue = new DerValue(derBytes);
        CertificateSerialNumber serialNumber = new CertificateSerialNumber(derValue);
        assert serialNumber != null : "Constructor with valid DerValue should not return null";
        String str = serialNumber.toString();
        assert str.contains("SerialNumber") || str.contains("1") : "toString() should include serial number value";
    }

    private static void testInvalidDerValue() {
        byte[] invalidDerBytes = {0x00}; // Invalid DER encoding for serial number
        try {
            DerValue invalidDerValue = new DerValue(invalidDerBytes);
            new CertificateSerialNumber(invalidDerValue);
            assert false : "Constructor with invalid DerValue should throw IOException";
        } catch (IOException e) {
            // Expected exception
        }
    }
}
