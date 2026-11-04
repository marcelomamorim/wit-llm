/*
 * @test
 * @summary Verify CertificateVersion(int) accepts V1..V3 and rejects other values with IOException
 * @modules java.base/sun.security.x509
 * @run main CertificateVersionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {
    public static void main(String[] args) throws Exception {
        if (CertificateVersion.V1 != 0 || CertificateVersion.V2 != 1 || CertificateVersion.V3 != 2) {
            throw new RuntimeException("unexpected version constants");
        }

        new CertificateVersion();

        int[] valid = {CertificateVersion.V1, CertificateVersion.V2, CertificateVersion.V3};
        for (int v : valid) {
            new CertificateVersion(v);
        }

        int[] invalid = {-1, 3, 4, 100, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int v : invalid) {
            boolean thrown = false;
            try {
                new CertificateVersion(v);
            } catch (IOException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("expected IOException for version " + v);
            }
        }
    }
}
