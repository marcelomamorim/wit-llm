/*
 * @test
 * @summary Verify CertificateVersion.encode output for V1, V2, V3 and round trip decoding
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificateVersionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import sun.security.util.DerInputStream;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws Exception {
        testDefaultVersionEncodesNothing();
        testV2Encoding();
        testV3Encoding();
        testRoundTrip();
        testInvalidVersionRejected();
    }

    static byte[] encode(CertificateVersion cv) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        cv.encode(bos);
        return bos.toByteArray();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testDefaultVersionEncodesNothing() throws IOException {
        byte[] out = encode(new CertificateVersion());
        check(out.length == 0, "V1 should encode to no bytes");
    }

    static void testV2Encoding() throws IOException {
        byte[] expected = {(byte) 0xA0, 0x03, 0x02, 0x01, 0x01};
        byte[] out = encode(new CertificateVersion(CertificateVersion.V2));
        check(Arrays.equals(expected, out), "V2 encoding mismatch");
    }

    static void testV3Encoding() throws IOException {
        byte[] expected = {(byte) 0xA0, 0x03, 0x02, 0x01, 0x02};
        byte[] out = encode(new CertificateVersion(CertificateVersion.V3));
        check(Arrays.equals(expected, out), "V3 encoding mismatch");
    }

    static void testRoundTrip() throws IOException {
        byte[] enc = encode(new CertificateVersion(CertificateVersion.V3));
        CertificateVersion decoded = new CertificateVersion(new DerInputStream(enc));
        byte[] reEnc = encode(decoded);
        check(Arrays.equals(enc, reEnc), "Round trip encoding mismatch");
    }

    static void testInvalidVersionRejected() {
        boolean thrown = false;
        try {
            new CertificateVersion(5);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "Unsupported version should be rejected");
    }
}
