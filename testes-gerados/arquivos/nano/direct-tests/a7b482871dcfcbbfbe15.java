/* @test    @summary Test default constructor of AlgIdDSA    @run main AlgIdDSAWitupTest */

import sun.security.x509.AlgIdDSA;

public class AlgIdDSAWitupTest {
    public static void main(String[] args) {
        testDefaultConstructor();
    }

    public static void testDefaultConstructor() {
        AlgIdDSA algId = new AlgIdDSA();
        if (algId == null) {
            throw new AssertionError("AlgIdDSA instance is null");
        }
        System.out.println("Default constructor test passed.");
    }
}
