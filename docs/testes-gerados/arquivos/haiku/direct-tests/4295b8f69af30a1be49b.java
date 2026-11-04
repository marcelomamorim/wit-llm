/*
 * @test
 * @summary Verify CertificateIssuerExtension.delete accepts the issuer attribute name case-insensitively and rejects other names with IOException
 * @modules java.base/sun.security.x509
 * @run main CertificateIssuerExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateIssuerExtension;

public class CertificateIssuerExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] emptySequence = new byte[] {0x30, 0x00};

        CertificateIssuerExtension ext =
                new CertificateIssuerExtension(Boolean.TRUE, emptySequence);

        ext.delete(CertificateIssuerExtension.ISSUER);
        ext.delete("ISSUER");
        ext.delete("Issuer");

        expectIOException(ext, CertificateIssuerExtension.NAME);
        expectIOException(ext, "other");
        expectIOException(ext, "");

        ext.delete("issuer");

        CertificateIssuerExtension ext2 =
                new CertificateIssuerExtension(Boolean.TRUE, emptySequence);
        ext2.delete("iSsUeR");
    }

    private static void expectIOException(CertificateIssuerExtension ext, String name) {
        try {
            ext.delete(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for attribute name: " + name);
    }
}
