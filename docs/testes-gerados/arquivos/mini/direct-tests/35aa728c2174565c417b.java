/*
 * @test
 * @summary Test CertificateExtensions constructor with DerInputStream
 * @run main CertificateExtensionsWitupTest
 */

import sun.security.x509.CertificateExtensions;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class CertificateExtensionsWitupTest {
    public static void main(String[] args) throws Exception {
        testEmptySequence();
        testInvalidSequence();
    }

    private static void testEmptySequence() throws IOException {
        // DerInputStream with empty sequence (DER encoding of empty SEQUENCE is 0x30 0x00)
        byte[] emptySeq = new byte[] {0x30, 0x00};
        DerInputStream in = new DerInputStream(emptySeq);
        CertificateExtensions ext = new CertificateExtensions(in);
        // No exception means success
        if (ext == null) {
            throw new RuntimeException("Constructor returned null");
        }
    }

    private static void testInvalidSequence() {
        // DerInputStream with invalid data (not a sequence)
        byte[] invalidData = new byte[] {0x31, 0x01, 0x00}; // SET tag instead of SEQUENCE
        DerInputStream in = new DerInputStream(invalidData);
        try {
            new CertificateExtensions(in);
            throw new RuntimeException("Expected IOException not thrown for invalid sequence");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
