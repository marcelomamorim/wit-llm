/*
 * @test
 * @summary Test CertificateSerialNumber constructor with InputStream
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.math.BigInteger;

public class CertificateSerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        testValidInputStream();
        testEmptyInputStream();
    }

    private static void testValidInputStream() throws IOException {
        // DER encoding of INTEGER 1: 0x02 0x01 0x01
        byte[] derEncodedOne = new byte[] { 0x02, 0x01, 0x01 };
        InputStream in = new ByteArrayInputStream(derEncodedOne);
        sun.security.x509.CertificateSerialNumber csn = new sun.security.x509.CertificateSerialNumber(in);
        String str = csn.toString();
        if (!"1".equals(str)) {
            throw new RuntimeException("Expected serial number string '1' but got: " + str);
        }
    }

    private static void testEmptyInputStream() {
        InputStream emptyIn = new ByteArrayInputStream(new byte[0]);
        try {
            new sun.security.x509.CertificateSerialNumber(emptyIn);
            throw new RuntimeException("Expected IOException for empty InputStream but none thrown");
        } catch (IOException e) {
            // Expected exception due to invalid DER encoding
        }
    }
}
