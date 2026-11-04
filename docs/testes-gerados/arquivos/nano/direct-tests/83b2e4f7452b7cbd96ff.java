/* @test    @summary Test constructor CertificateSerialNumber(DerInputStream in) for deserialization behavior.    @run main CertificateSerialNumberWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.security.util.DerInputStream;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws IOException {
        testValidDerInput();
        testInvalidDerInput();
    }

    private static void testValidDerInput() throws IOException {
        byte[] validDerBytes = {0x02, 0x01, 0x0A}; // DER encoding for integer 10
        InputStream inStream = new ByteArrayInputStream(validDerBytes);
        DerInputStream derIn = new DerInputStream(inStream);
        CertificateSerialNumber serialNumber = new CertificateSerialNumber(derIn);
        String str = serialNumber.toString();
        if (str == null || str.isEmpty()) {
            throw new AssertionError("Expected non-empty string for valid DER input");
        }
        System.out.println("Valid DER input test passed: " + str);
    }

    private static void testInvalidDerInput() {
        byte[] invalidDerBytes = {(byte)0xFF, 0x00}; // invalid DER encoding
        InputStream inStream = new ByteArrayInputStream(invalidDerBytes);
        try {
            DerInputStream derIn = new DerInputStream(inStream);
            new CertificateSerialNumber(derIn);
            throw new AssertionError("Expected IOException for invalid DER input");
        } catch (IOException e) {
            System.out.println("Invalid DER input test passed: Caught expected IOException");
        }
    }
}
