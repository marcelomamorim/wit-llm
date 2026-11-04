/*
 * @test
 * @summary CertificateExtensions(DerInputStream) accepts an empty DER SEQUENCE and rejects a truncated one with IOException
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificateExtensionsWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.x509.CertificateExtensions;

public class CertificateExtensionsWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] emptySequence = {0x30, 0x00};
        CertificateExtensions ce =
                new CertificateExtensions(new DerInputStream(emptySequence));
        if (ce == null) {
            throw new RuntimeException("constructor returned null");
        }

        boolean threw = false;
        try {
            byte[] truncated = {0x30, 0x02, 0x01};
            new CertificateExtensions(new DerInputStream(truncated));
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("expected IOException for truncated DER input");
        }
    }
}
