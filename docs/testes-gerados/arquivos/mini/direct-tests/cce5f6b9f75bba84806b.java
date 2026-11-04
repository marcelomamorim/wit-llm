/*
 * @test
 * @summary Test default constructor of sun.security.provider.DSAPublicKey
 * @run main DSAPublicKeyWitupTest
 */

import sun.security.provider.DSAPublicKey;

public class DSAPublicKeyWitupTest {
    public static void main(String[] args) throws Exception {
        DSAPublicKey key = new DSAPublicKey();
        if (key == null) {
            throw new RuntimeException("DSAPublicKey default constructor returned null");
        }
        // The default constructor does not initialize key material or parameters.
        // We test that no exception is thrown and the object is created.
        System.out.println("DSAPublicKey default constructor test passed.");
    }
}
