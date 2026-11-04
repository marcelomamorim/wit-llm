/*
 * @test
 * @summary Witup-generated checks for CertificateVersion(InputStream) constructor behavior
 * @modules java.base/sun.security.x509
 * @run main CertificateVersionWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws Exception {
        if (CertificateVersion.V3 != 2) {
            throw new RuntimeException("Unexpected V3 constant: " + CertificateVersion.V3);
        }

        byte[] explicitV3 = {(byte) 0xA0, 0x03, 0x02, 0x01, 0x02};
        new CertificateVersion(new ByteArrayInputStream(explicitV3));

        byte[] primitiveInteger = {0x02, 0x01, 0x01};
        new CertificateVersion(new ByteArrayInputStream(primitiveInteger));

        expectIOException(new byte[0]);

        byte[] trailingInsideWrapper = {(byte) 0xA0, 0x04, 0x02, 0x01, 0x02, 0x00};
        expectIOException(trailingInsideWrapper);
    }

    private static void expectIOException(byte[] data) {
        try {
            new CertificateVersion(new ByteArrayInputStream(data));
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("Expected IOException for input length " + data.length);
    }
}
