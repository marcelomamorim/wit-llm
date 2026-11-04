/*
 * @test
 * @summary Checks CertificateExtensions(DerInputStream) accepts an empty SEQUENCE and rejects a non-SEQUENCE tag and a truncated encoding with IOException
 * @modules java.base/sun.security.util
 *          java.base/sun.security.x509
 * @run main CertificateExtensionsWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.x509.CertificateExtensions;

public class CertificateExtensionsWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptySequenceIsAccepted();
        testNonSequenceTagThrowsIOException();
        testTruncatedSequenceThrowsIOException();
    }

    private static void testEmptySequenceIsAccepted() throws Exception {
        byte[] encoded = new byte[] {0x30, 0x00};
        CertificateExtensions ext = new CertificateExtensions(new DerInputStream(encoded));
        if (ext == null) {
            throw new RuntimeException("Expected non-null CertificateExtensions for empty SEQUENCE");
        }
    }

    private static void testNonSequenceTagThrowsIOException() throws Exception {
        byte[] encoded = new byte[] {0x02, 0x00};
        boolean thrown = false;
        try {
            new CertificateExtensions(new DerInputStream(encoded));
        } catch (IOException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for non-SEQUENCE tag");
        }
    }

    private static void testTruncatedSequenceThrowsIOException() throws Exception {
        byte[] encoded = new byte[] {0x30};
        boolean thrown = false;
        try {
            new CertificateExtensions(new DerInputStream(encoded));
        } catch (IOException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for truncated SEQUENCE");
        }
    }
}
