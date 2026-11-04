/*
 * @test
 * @summary Basic observable behavior of CertificateVersion(InputStream) constructor
 * @modules java.base/sun.security.x509
 * @run main CertificateVersionWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws Exception {
        if (CertificateVersion.V1 != 0 || CertificateVersion.V2 != 1 || CertificateVersion.V3 != 2) {
            throw new RuntimeException("unexpected version constants");
        }

        // Explicitly tagged [0] INTEGER encodings of V1, V2 and V3 must be accepted
        new CertificateVersion(stream(0xA0, 0x03, 0x02, 0x01, 0x00));
        new CertificateVersion(stream(0xA0, 0x03, 0x02, 0x01, 0x01));
        new CertificateVersion(stream(0xA0, 0x03, 0x02, 0x01, 0x02));

        // A plain (not context-specific) INTEGER is not wrapped; construct() leaves the default and does not throw
        new CertificateVersion(stream(0x02, 0x01, 0x02));

        // Empty input: DER value cannot be read
        expectIOException(new byte[0]);

        // Truncated explicit-tagged value
        expectIOException(new byte[] {(byte) 0xA0, 0x03, 0x02});
    }

    private static InputStream stream(int... bytes) {
        byte[] b = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            b[i] = (byte) bytes[i];
        }
        return new ByteArrayInputStream(b);
    }

    private static void expectIOException(byte[] data) {
        try {
            new CertificateVersion(new ByteArrayInputStream(data));
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException was not thrown");
    }
}
