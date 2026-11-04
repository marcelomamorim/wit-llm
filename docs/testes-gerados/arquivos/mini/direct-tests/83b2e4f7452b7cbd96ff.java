/*
 * @test
 * @summary Test CertificateSerialNumber constructor with DerInputStream
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerInputStream();
        testInvalidDerInputStream();
    }

    private static void testValidDerInputStream() throws IOException {
        // DER encoding of INTEGER 1: 0x02 0x01 0x01
        byte[] validDer = new byte[] {0x02, 0x01, 0x01};
        DerInputStream derIn = new DerInputStream(validDer);
        CertificateSerialNumber csn = new CertificateSerialNumber(derIn);
        String str = csn.toString();
        if (!"1".equals(str)) {
            throw new RuntimeException("Expected serial number string '1' but got: " + str);
        }
    }

    private static void testInvalidDerInputStream() {
        // Invalid DER encoding: empty array
        byte[] invalidDer = new byte[0];
        DerInputStream derIn = new DerInputStream(invalidDer);
        try {
            new CertificateSerialNumber(derIn);
            throw new RuntimeException("Expected IOException for empty DerInputStream input");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
