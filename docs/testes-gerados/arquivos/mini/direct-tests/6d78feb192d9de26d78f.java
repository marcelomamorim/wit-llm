/*
 * @test
 * @summary Test CertificateVersion constructor with DerInputStream
 * @run main CertificateVersionWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {
    public static void main(String[] args) throws Exception {
        testValidVersion();
        testInvalidVersionExtraData();
    }

    private static void testValidVersion() throws IOException {
        // Construct DER encoding for version 2 (V3 = 2)
        // Encoding: [0] EXPLICIT INTEGER 2
        // DER encoding:  A0 03 02 01 02
        byte[] derEncoded = new byte[] {(byte)0xA0, 0x03, 0x02, 0x01, 0x02};
        DerInputStream derIn = new DerInputStream(derEncoded);
        CertificateVersion cv = new CertificateVersion(derIn);
        // The version field is private, but getVersion() is private too,
        // so we test by encoding and decoding again or by no exception thrown.
        // Here we rely on no exception and that constructor accepts version 2.
    }

    private static void testInvalidVersionExtraData() {
        // Construct DER encoding with extra data after integer
        // Encoding: [0] EXPLICIT INTEGER 2 plus extra byte 0x00
        // DER encoding: A0 04 02 01 02 00
        byte[] derEncoded = new byte[] {(byte)0xA0, 0x04, 0x02, 0x01, 0x02, 0x00};
        DerInputStream derIn = new DerInputStream(derEncoded);
        try {
            new CertificateVersion(derIn);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"X.509 version, bad format".equals(msg)) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
