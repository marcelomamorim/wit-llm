/*
 * @test
 * @summary Verifies that the public no-argument constructor of X509CertificatePair creates a new non-null instance on each call
 * @modules java.base/sun.security.provider.certpath
 * @run main X509CertificatePairWitupTest
 */
import sun.security.provider.certpath.X509CertificatePair;

public class X509CertificatePairWitupTest {
    public static void main(String[] args) {
        X509CertificatePair first = new X509CertificatePair();
        if (first == null) {
            throw new RuntimeException("Constructor returned null");
        }
        X509CertificatePair second = new X509CertificatePair();
        if (second == null) {
            throw new RuntimeException("Second constructor call returned null");
        }
        if (first == second) {
            throw new RuntimeException("Constructor returned the same instance on repeated calls");
        }
    }
}
