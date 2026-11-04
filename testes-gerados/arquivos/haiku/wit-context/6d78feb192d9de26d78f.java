/*
 * @test
 * @summary Checks public CertificateVersion(InputStream) decoding of explicit, implicit and empty DER input
 * @modules java.base/sun.security.x509
 * @run main CertificateVersionWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] explicitV3 = {(byte) 0xA0, 0x03, 0x02, 0x01, 0x02};
        new CertificateVersion(new ByteArrayInputStream(explicitV3));

        byte[] untaggedSequence = {0x30, 0x00};
        new CertificateVersion(new ByteArrayInputStream(untaggedSequence));

        boolean thrown = false;
        try {
            new CertificateVersion(new ByteArrayInputStream(new byte[0]));
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for empty input");
        }
    }
}
