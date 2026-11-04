/*
 * @test
 * @summary Test CertificateSerialNumber constructor with InputStream for IOException paths
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        testValidInputStream();
        testEmptyInputStream();
        testInvalidTagInputStream();
    }

    private static void testValidInputStream() throws Exception {
        // Construct a valid DER encoding of a serial number (INTEGER tag = 0x02)
        // Encoding: 0x02 0x01 0x01  (INTEGER of length 1 with value 1)
        byte[] validDer = new byte[] { 0x02, 0x01, 0x01 };
        InputStream in = new ByteArrayInputStream(validDer);
        CertificateSerialNumber csn = new CertificateSerialNumber(in);
        String str = csn.toString();
        if (!str.equals("1")) {
            throw new RuntimeException("Expected serial number string '1', got: " + str);
        }
    }

    private static void testEmptyInputStream() {
        // Empty InputStream should cause IOException when reading
        InputStream emptyIn = new ByteArrayInputStream(new byte[0]);
        try {
            new CertificateSerialNumber(emptyIn);
            throw new RuntimeException("Expected IOException for empty InputStream");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testInvalidTagInputStream() {
        // Provide DER encoding with invalid tag (not 0x02 INTEGER)
        // For example, tag 0x03 (BIT STRING) with some length and data
        byte[] invalidTagDer = new byte[] { 0x03, 0x01, 0x00 };
        InputStream in = new ByteArrayInputStream(invalidTagDer);
        try {
            new CertificateSerialNumber(in);
            throw new RuntimeException("Expected IOException for invalid tag in InputStream");
        } catch (IOException e) {
            // Expected
        }
    }
}
