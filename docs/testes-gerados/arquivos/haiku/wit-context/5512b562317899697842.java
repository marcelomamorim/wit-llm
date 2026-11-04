/*
 * @test
 * @summary Verify that the public no-argument DSAPrivateKey constructor creates an instance implementing the DSA private key interface
 * @modules java.base/sun.security.provider
 * @run main DSAPrivateKeyWitupTest
 */

import sun.security.provider.DSAPrivateKey;

public class DSAPrivateKeyWitupTest {

    public static void main(String[] args) throws Exception {
        DSAPrivateKey key = new DSAPrivateKey();
        if (key == null) {
            throw new RuntimeException("constructor returned null");
        }
        if (!(key instanceof java.security.interfaces.DSAPrivateKey)) {
            throw new RuntimeException("instance does not implement java.security.interfaces.DSAPrivateKey");
        }
    }
}
