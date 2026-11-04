/* @test    @summary Test default constructor of X509CertificatePair    @run main X509CertificatePairWitupTest */

import sun.security.provider.certpath.X509CertificatePair;

public class X509CertificatePairWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
    }

    public static void testDefaultConstructor() {
        X509CertificatePair instance = new X509CertificatePair();
        if (instance == null) {
            throw new AssertionError("X509CertificatePair instance is null");
        }
    }
}
