/*
 * @test
 * @summary Test default constructor of AlgIdDSA
 * @run main sun.security.x509.AlgIdDSAWitupTest
 */

public class AlgIdDSAWitupTest {
    public static void main(String[] args) {
        // Test default constructor
        sun.security.x509.AlgIdDSA algId = new sun.security.x509.AlgIdDSA();
        // The default constructor does not initialize parameters, so getP, getQ, getG should return null
        if (algId.getP() != null) {
            throw new RuntimeException("Expected getP() to return null");
        }
        if (algId.getQ() != null) {
            throw new RuntimeException("Expected getQ() to return null");
        }
        if (algId.getG() != null) {
            throw new RuntimeException("Expected getG() to return null");
        }
    }
}
