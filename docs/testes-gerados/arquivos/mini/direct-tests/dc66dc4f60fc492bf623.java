/*
 * @test
 * @summary Test CertificateVersion constructor with InputStream
 * @run main CertificateVersionWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.x509.CertificateVersion;
import sun.security.util.DerValue;

public class CertificateVersionWitupTest {
    public static void main(String[] args) throws Exception {
        testValidVersion1();
        testValidVersion2();
        testValidVersion3();
        testInvalidVersionExtraData();
    }

    private static void testValidVersion1() throws IOException {
        // Version 1 encoded as [0] EXPLICIT INTEGER 0
        // DER encoding:  A0 03 02 01 00
        byte[] encoded = new byte[] {(byte)0xA0, 0x03, 0x02, 0x01, 0x00};
        ByteArrayInputStream bais = new ByteArrayInputStream(encoded);
        CertificateVersion cv = new CertificateVersion(bais);
        // The version field is private, so we test by encoding and decoding again
        // or by checking no exception and successful construction
    }

    private static void testValidVersion2() throws IOException {
        // Version 2 encoded as [0] EXPLICIT INTEGER 1
        // DER encoding:  A0 03 02 01 01
        byte[] encoded = new byte[] {(byte)0xA0, 0x03, 0x02, 0x01, 0x01};
        ByteArrayInputStream bais = new ByteArrayInputStream(encoded);
        CertificateVersion cv = new CertificateVersion(bais);
    }

    private static void testValidVersion3() throws IOException {
        // Version 3 encoded as [0] EXPLICIT INTEGER 2
        // DER encoding:  A0 03 02 01 02
        byte[] encoded = new byte[] {(byte)0xA0, 0x03, 0x02, 0x01, 0x02};
        ByteArrayInputStream bais = new ByteArrayInputStream(encoded);
        CertificateVersion cv = new CertificateVersion(bais);
    }

    private static void testInvalidVersionExtraData() {
        // Construct a DER encoding with extra data after the integer
        // e.g. A0 05 02 01 00 00 00 (extra 2 zero bytes after integer)
        byte[] encoded = new byte[] {(byte)0xA0, 0x05, 0x02, 0x01, 0x00, 0x00, 0x00};
        ByteArrayInputStream bais = new ByteArrayInputStream(encoded);
        try {
            new CertificateVersion(bais);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"X.509 version, bad format".equals(msg)) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
