/*
 * @test
 * @summary Test sun.security.x509.CertificateVersion.encode method behavior
 * @run main sun.security.x509.CertificateVersionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import sun.security.x509.CertificateVersion;
import sun.security.util.DerValue;
import sun.security.util.DerOutputStream;
import java.io.OutputStream;

public class CertificateVersionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeV1ProducesNoOutput();
        testEncodeV2ProducesExpectedEncoding();
        testEncodeV3ProducesExpectedEncoding();
    }

    private static void testEncodeV1ProducesNoOutput() throws IOException {
        CertificateVersion cv = new CertificateVersion(CertificateVersion.V1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cv.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded.length != 0) {
            throw new RuntimeException("encode() for V1 should produce no output, but produced " + encoded.length + " bytes");
        }
    }

    private static void testEncodeV2ProducesExpectedEncoding() throws IOException {
        CertificateVersion cv = new CertificateVersion(CertificateVersion.V2);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cv.encode(baos);
        byte[] encoded = baos.toByteArray();

        // Manually build expected encoding:
        // Context-specific constructed tag 0, containing integer 1 (V2)
        DerOutputStream tmp = new DerOutputStream();
        tmp.putInteger(CertificateVersion.V2);
        DerOutputStream seq = new DerOutputStream();
        seq.write(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte)0), tmp);
        byte[] expected = seq.toByteArray();

        if (!Arrays.equals(encoded, expected)) {
            throw new RuntimeException("encode() for V2 produced unexpected encoding");
        }
    }

    private static void testEncodeV3ProducesExpectedEncoding() throws IOException {
        CertificateVersion cv = new CertificateVersion(CertificateVersion.V3);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cv.encode(baos);
        byte[] encoded = baos.toByteArray();

        // Manually build expected encoding:
        DerOutputStream tmp = new DerOutputStream();
        tmp.putInteger(CertificateVersion.V3);
        DerOutputStream seq = new DerOutputStream();
        seq.write(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte)0), tmp);
        byte[] expected = seq.toByteArray();

        if (!Arrays.equals(encoded, expected)) {
            throw new RuntimeException("encode() for V3 produced unexpected encoding");
        }
    }
}
