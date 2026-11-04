/*
 * @test
 * @summary Test sun.security.x509.CertificateExtensions constructor with DerInputStream
 * @run main CertificateExtensionsWitupTest
 */

import sun.security.x509.CertificateExtensions;
import sun.security.util.DerInputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;

public class CertificateExtensionsWitupTest {
    public static void main(String[] args) throws Exception {
        testValidEmptySequence();
        testInvalidTagThrowsIOException();
        testEmptyInputThrowsIOException();
    }

    private static void testValidEmptySequence() throws Exception {
        // DER encoding of empty SEQUENCE: 0x30 0x00
        byte[] derEmptySequence = new byte[] {0x30, 0x00};
        DerInputStream in = new DerInputStream(derEmptySequence);
        CertificateExtensions ext = new CertificateExtensions(in);
        if (ext == null) {
            throw new RuntimeException("Constructor returned null");
        }
    }

    private static void testInvalidTagThrowsIOException() {
        // DER encoding with invalid tag (not 0x30)
        byte[] invalidTag = new byte[] {0x31, 0x00}; // tag 0x31 is not SEQUENCE
        DerInputStream in = new DerInputStream(invalidTag);
        try {
            new CertificateExtensions(in);
            throw new RuntimeException("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testEmptyInputThrowsIOException() {
        // Empty input should cause IOException when reading sequence
        byte[] empty = new byte[0];
        DerInputStream in = new DerInputStream(empty);
        try {
            new CertificateExtensions(in);
            throw new RuntimeException("Expected IOException not thrown for empty input");
        } catch (IOException e) {
            // Expected
        }
    }
}
