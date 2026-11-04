/*
 * @test
 * @summary Basic test for sun.security.provider.DSAPrivateKey default constructor
 * @run main DSAPrivateKeyWitupTest
 */

import sun.security.provider.DSAPrivateKey;

public class DSAPrivateKeyWitupTest {
    public static void main(String[] args) throws Exception {
        DSAPrivateKey key = new DSAPrivateKey();
        if (key == null) {
            throw new RuntimeException("DSAPrivateKey() constructor returned null");
        }
        // The default constructor does not initialize key material or parameters
        // so getParams() should return null
        if (key.getParams() != null) {
            throw new RuntimeException("Expected null parameters from default constructor");
        }
        // getX() should return null since x is not initialized
        if (key.getX() != null) {
            throw new RuntimeException("Expected null private value x from default constructor");
        }
        // getAlgorithm() should return "DSA"
        if (!"DSA".equals(key.getAlgorithm())) {
            throw new RuntimeException("Expected algorithm to be 'DSA'");
        }
        // getFormat() should return "PKCS#8"
        if (!"PKCS#8".equals(key.getFormat())) {
            throw new RuntimeException("Expected format to be 'PKCS#8'");
        }
        // getEncoded() should return null or empty since key is not initialized
        byte[] encoded = key.getEncoded();
        if (encoded != null && encoded.length != 0) {
            throw new RuntimeException("Expected null or empty encoding from default constructor");
        }
    }
}
