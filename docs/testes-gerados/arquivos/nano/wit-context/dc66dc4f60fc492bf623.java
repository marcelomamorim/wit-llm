/* @test    @summary Test deserialization constructor of CertificateVersion with InputStream    @run main CertificateVersionWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) {
        testValidInputStream();
        testInvalidInputStream();
    }

    private static void testValidInputStream() {
        try {
            byte[] validBytes = {0x30, 0x03, 0x02, 0x01, 0x00}; // DER encoding for version 1
            ByteArrayInputStream bais = new ByteArrayInputStream(validBytes);
            CertificateVersion cv = new CertificateVersion(bais);
            if (cv.getVersion() != CertificateVersion.V1) {
                throw new AssertionError("Expected version V1, got " + cv.getVersion());
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }

    private static void testInvalidInputStream() {
        try {
            byte[] invalidBytes = {(byte)0x30, (byte)0x80, (byte)0x02, (byte)0x01, (byte)0x00}; // Malformed DER
            ByteArrayInputStream bais = new ByteArrayInputStream(invalidBytes);
            new CertificateVersion(bais);
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Expected exception, test passes
        }
    }
}
