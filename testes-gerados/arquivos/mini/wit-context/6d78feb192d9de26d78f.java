/*
 * @test
 * @summary Test CertificateVersion constructor with DerInputStream for valid and invalid inputs
 * @run main CertificateVersionWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {
    public static void main(String[] args) throws Exception {
        testValidVersion();
        testEmptyStreamThrowsIOException();
        testMalformedDerValueThrowsIOException();
    }

    private static void testValidVersion() throws Exception {
        // DER encoding for [0] EXPLICIT INTEGER 2 (version 3)
        // Tag 0xA0 (context-specific constructed 0), length 3, INTEGER tag 0x02, length 1, value 2
        byte[] derEncoded = new byte[] {(byte)0xA0, 0x03, 0x02, 0x01, 0x02};
        DerInputStream derInput = new DerInputStream(derEncoded);
        CertificateVersion cv = new CertificateVersion(derInput);
        // The version should be 2 (V3)
        if (cv.getVersion() != CertificateVersion.V3) {
            throw new RuntimeException("Expected version 2 (V3), got " + cv.getVersion());
        }
    }

    private static void testEmptyStreamThrowsIOException() {
        // Empty input stream should cause DerInputStream.getDerValue() to throw IOException
        byte[] empty = new byte[0];
        DerInputStream derInput = new DerInputStream(empty);
        try {
            new CertificateVersion(derInput);
            throw new RuntimeException("Expected IOException for empty input stream");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testMalformedDerValueThrowsIOException() {
        // Malformed DER: context-specific constructed tag but with extra trailing bytes
        // Tag 0xA0, length 4, INTEGER tag 0x02, length 1, value 1, plus extra byte 0x00
        byte[] malformed = new byte[] {(byte)0xA0, 0x04, 0x02, 0x01, 0x01, 0x00};
        DerInputStream derInput = new DerInputStream(malformed);
        try {
            new CertificateVersion(derInput);
            throw new RuntimeException("Expected IOException for malformed DER with trailing bytes");
        } catch (IOException e) {
            // Expected
        }
    }
}
