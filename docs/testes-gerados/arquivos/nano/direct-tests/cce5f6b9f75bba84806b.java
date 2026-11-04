/* @test    @summary Test default constructor of DSAPublicKey for instantiation.    @run main DSAPublicKeyWitupTest */

import sun.security.provider.DSAPublicKey;

public class DSAPublicKeyWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
    }

    public static void testDefaultConstructor() {
        DSAPublicKey key = new DSAPublicKey();
        if (key == null) {
            throw new AssertionError("DSAPublicKey default constructor returned null");
        }
        System.out.println("DSAPublicKey default constructor test passed.");
    }
}
