/* @test    @summary Test encoding of CertificateVersion for version 1, 2, and 3    @run main CertificateVersionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeVersion1();
        testEncodeVersion2();
        testEncodeVersion3();
    }

    static void testEncodeVersion1() throws IOException {
        CertificateVersion cv = new CertificateVersion(CertificateVersion.V1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cv.encode(baos);
        byte[] result = baos.toByteArray();
        // For version 1, encode should produce an empty byte array
        assert result.length == 0 : "Expected empty encoding for version 1, got length " + result.length;
    }

    static void testEncodeVersion2() throws IOException {
        CertificateVersion cv = new CertificateVersion(CertificateVersion.V2);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cv.encode(baos);
        byte[] result = baos.toByteArray();
        // For version 2, encode should produce a sequence with context-specific tag
        assert result.length > 0 : "Encoding for version 2 should not be empty";
        // Check that the first byte indicates a context-specific constructed tag (0xA0)
        assert (result[0] & 0xFF) == 0xA0 : "Expected context-specific constructed tag 0xA0, got 0x" + Integer.toHexString(result[0] & 0xFF);
    }

    static void testEncodeVersion3() throws IOException {
        CertificateVersion cv = new CertificateVersion(CertificateVersion.V3);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cv.encode(baos);
        byte[] result = baos.toByteArray();
        // For version 3, encode should produce a sequence with context-specific tag
        assert result.length > 0 : "Encoding for version 3 should not be empty";
        // Check that the first byte indicates a context-specific constructed tag (0xA0)
        assert (result[0] & 0xFF) == 0xA0 : "Expected context-specific constructed tag 0xA0, got 0x" + Integer.toHexString(result[0] & 0xFF);
    }
}
