/*
 * @test
 * @summary Verifies that the no-argument DSAPublicKey constructor creates a usable, non-null key instance without throwing.
 * @modules java.base/sun.security.provider
 * @run main DSAPublicKeyWitupTest
 */

import java.io.Serializable;
import java.security.PublicKey;
import sun.security.provider.DSAPublicKey;

public class DSAPublicKeyWitupTest {

    public static void main(String[] args) throws Exception {
        DSAPublicKey key = new DSAPublicKey();
        if (key == null) {
            throw new RuntimeException("constructor returned null");
        }
        if (!(key instanceof PublicKey)) {
            throw new RuntimeException("key is not a java.security.PublicKey");
        }
        if (!(key instanceof Serializable)) {
            throw new RuntimeException("key is not Serializable");
        }

        DSAPublicKey other = new DSAPublicKey();
        if (key == other) {
            throw new RuntimeException("constructor returned a shared instance");
        }
    }
}
