/*
 * @test
 * @summary Checks public CertificateVersion(DerInputStream) constructor on valid and malformed DER input
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificateVersionWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws Exception {
        if (CertificateVersion.V1 != 0 || CertificateVersion.V2 != 1 || CertificateVersion.V3 != 2) {
            throw new RuntimeException("Unexpected version constants");
        }

        // [0] EXPLICIT { INTEGER 2 } -> valid, must not throw
        byte[] v3 = {(byte) 0xA0, 0x03, 0x02, 0x01, 0x02};
        new CertificateVersion(new DerInputStream(v3));

        // Plain INTEGER without context-specific wrapper -> accepted, no exception
        byte[] plain = {0x02, 0x01, 0x01};
        new CertificateVersion(new DerInputStream(plain));

        // Empty input -> reading DerValue must fail with IOException
        expectIOException(new byte[0]);

        // Context-specific wrapper containing OCTET STRING instead of INTEGER -> IOException
        byte[] octet = {(byte) 0xA0, 0x03, 0x04, 0x01, 0x02};
        expectIOException(octet);
    }

    private static void expectIOException(byte[] data) {
        try {
            new CertificateVersion(new DerInputStream(data));
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException was not thrown");
    }
}
