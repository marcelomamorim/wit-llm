/*
 * @test
 * @summary Test sun.security.x509.CertificateVersion.encode(OutputStream) for normal and edge cases
 * @run main CertificateVersionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeV1DoesNothing();
        testEncodeV2ProducesExpectedEncoding();
        testEncodeV3ProducesExpectedEncoding();
    }

    private static void testEncodeV1DoesNothing() throws IOException {
        CertificateVersion cv = new CertificateVersion(CertificateVersion.V1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cv.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded.length != 0) {
            throw new RuntimeException("encode() for V1 should produce empty output, but got length " + encoded.length);
        }
    }

    private static void testEncodeV2ProducesExpectedEncoding() throws IOException {
        CertificateVersion cv = new CertificateVersion(CertificateVersion.V2);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cv.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("encode() for V2 should produce non-empty output");
        }
        // Basic sanity check: encoded output should contain the version number 1 (V2) as integer
        boolean foundVersion = false;
        for (byte b : encoded) {
            if (b == 1) { // V2 constant
                foundVersion = true;
                break;
            }
        }
        if (!foundVersion) {
            throw new RuntimeException("encode() output for V2 does not contain expected version byte");
        }
    }

    private static void testEncodeV3ProducesExpectedEncoding() throws IOException {
        CertificateVersion cv = new CertificateVersion(CertificateVersion.V3);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cv.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("encode() for V3 should produce non-empty output");
        }
        // Basic sanity check: encoded output should contain the version number 2 (V3) as integer
        boolean foundVersion = false;
        for (byte b : encoded) {
            if (b == 2) { // V3 constant
                foundVersion = true;
                break;
            }
        }
        if (!foundVersion) {
            throw new RuntimeException("encode() output for V3 does not contain expected version byte");
        }
    }
}
