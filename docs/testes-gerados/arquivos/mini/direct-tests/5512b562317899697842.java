/*
 * @test
 * @summary Test default constructor of DSAPrivateKey
 * @run main DSAPrivateKeyWitupTest
 */

import sun.security.provider.DSAPrivateKey;
import java.security.interfaces.DSAPrivateKey as DSAPrivateKeyInterface;
import java.security.interfaces.DSAParams;

public class DSAPrivateKeyWitupTest {
    public static void main(String[] args) throws Exception {
        DSAPrivateKey key = new DSAPrivateKey();
        if (key == null) {
            throw new RuntimeException("DSAPrivateKey default constructor returned null");
        }
        // The default constructor does not initialize the key or parameters
        // so getParams() should return null
        DSAParams params = key.getParams();
        if (params != null) {
            throw new RuntimeException("Expected null parameters from default constructor");
        }
        // The private value x is not accessible, but getParams() is public and observable
        // No exception should be thrown
    }
}
