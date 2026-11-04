/*
 * @test
 * @summary Test CertificateSerialNumber constructor with DerInputStream for IOException paths
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import sun.security.util.DerInputStream;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerInputStream();
        testDerInputStreamEmpty();
        testDerInputStreamInvalidLength();
    }

    private static void testValidDerInputStream() throws Exception {
        // Construct a valid DER encoding of an integer serial number
        // DER encoding for integer 1: 0x02 0x01 0x01
        byte[] validDer = new byte[] { 0x02, 0x01, 0x01 };
        DerInputStream derIn = new DerInputStream(validDer);
        CertificateSerialNumber csn = new CertificateSerialNumber(derIn);
        String str = csn.toString();
        if (!str.equals("1")) {
            throw new RuntimeException("Expected serial number string '1', got: " + str);
        }
    }

    private static void testDerInputStreamEmpty() {
        // Empty input stream should cause IOException
        byte[] empty = new byte[0];
        DerInputStream derIn = new DerInputStream(empty);
        try {
            new CertificateSerialNumber(derIn);
            throw new RuntimeException("Expected IOException for empty DerInputStream");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testDerInputStreamInvalidLength() {
        // Construct invalid DER encoding with invalid length byte
        // For example: 0x02 (integer tag), length byte invalid (e.g. 0x80 indefinite length not supported)
        byte[] invalidLength = new byte[] { 0x02, (byte)0x80 };
        DerInputStream derIn = new DerInputStream(invalidLength);
        try {
            new CertificateSerialNumber(derIn);
            throw new RuntimeException("Expected IOException for invalid length in DerInputStream");
        } catch (IOException e) {
            // Expected
        }
    }
}
