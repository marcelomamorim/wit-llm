/*
 * @test
 * @summary Observable behavior of the no-arg DSAPublicKey constructor
 * @modules java.base/sun.security.provider
 * @run main DSAPublicKeyWitupTest
 */

import sun.security.provider.DSAPublicKey;

public class DSAPublicKeyWitupTest {

    public static void main(String[] args) throws Exception {
        DSAPublicKey key = new DSAPublicKey();
        if (key == null) {
            throw new RuntimeException("constructor returned null");
        }
        if (!(key instanceof java.security.interfaces.DSAPublicKey)) {
            throw new RuntimeException("instance does not implement java.security.interfaces.DSAPublicKey");
        }
        if (!"X.509".equals(key.getFormat())) {
            throw new RuntimeException("unexpected format: " + key.getFormat());
        }
    }
}
