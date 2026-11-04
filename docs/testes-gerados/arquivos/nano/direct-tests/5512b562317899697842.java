/* @test    @summary Test default constructor of DSAPrivateKey for instantiation and basic behavior    @run main DSAPrivateKeyWitupTest */

import sun.security.provider.DSAPrivateKey;

public class DSAPrivateKeyWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
    }

    public static void testDefaultConstructor() {
        DSAPrivateKey key = new DSAPrivateKey();
        if (key == null) {
            throw new AssertionError("DSAPrivateKey instance is null");
        }
        System.out.println("Default constructor test passed.");
    }
}
