/*
 * @test
 * @summary Basic test for the default constructor of X509CertificatePair
 * @run main X509CertificatePairWitupTest
 */

import sun.security.provider.certpath.X509CertificatePair;

public class X509CertificatePairWitupTest {
    public static void main(String[] args) {
        // Test default constructor creates non-null instance
        X509CertificatePair pair = new X509CertificatePair();
        if (pair == null) {
            throw new RuntimeException("X509CertificatePair() constructor returned null");
        }
    }
}
