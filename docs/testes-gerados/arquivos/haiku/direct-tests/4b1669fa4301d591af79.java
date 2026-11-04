/*
 * @test
 * @summary Verify CertificateVersion(int) accepts V1..V3 and rejects other versions with IOException
 * @modules java.base/sun.security.x509
 * @run main CertificateVersionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws Exception {
        new CertificateVersion();

        int[] valid = {CertificateVersion.V1, CertificateVersion.V2, CertificateVersion.V3};
        for (int v : valid) {
            new CertificateVersion(v);
        }

        int[] invalid = {-1, 3, 4, 100, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int v : invalid) {
            expectIOException(v);
        }
    }

    static void expectIOException(int v) {
        try {
            new CertificateVersion(v);
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not supported")) {
                throw new RuntimeException("Unexpected message for version " + v + ": " + msg);
            }
            return;
        }
        throw new RuntimeException("No IOException for unsupported version " + v);
    }
}
