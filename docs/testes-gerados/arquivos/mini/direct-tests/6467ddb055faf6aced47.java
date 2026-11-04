/*
 * @test
 * @summary Test default constructor of X509CertificatePair
 * @run main X509CertificatePairWitupTest
 */

public class X509CertificatePairWitupTest {
    public static void main(String[] args) throws Exception {
        X509CertificatePair pair = new X509CertificatePair();
        if (pair == null) {
            throw new RuntimeException("Constructor returned null");
        }
        // The default constructor should create an instance with null forward and reverse
        if (pair.forward != null) {
            throw new RuntimeException("Expected forward to be null");
        }
        if (pair.reverse != null) {
            throw new RuntimeException("Expected reverse to be null");
        }
    }
}
