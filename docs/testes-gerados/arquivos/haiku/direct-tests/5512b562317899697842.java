/*
 * @test
 * @summary Verifies the public no-arg constructor of sun.security.provider.DSAPrivateKey creates a usable, unparameterized key instance
 * @modules java.base/sun.security.provider
 * @run main DSAPrivateKeyWitupTest
 */

import java.io.Serializable;
import sun.security.provider.DSAPrivateKey;

public class DSAPrivateKeyWitupTest {

    public static void main(String[] args) throws Exception {
        DSAPrivateKey key = new DSAPrivateKey();

        if (key == null) {
            throw new RuntimeException("Constructor returned null");
        }
        if (!(key instanceof java.security.interfaces.DSAPrivateKey)) {
            throw new RuntimeException("Instance is not a java.security.interfaces.DSAPrivateKey");
        }
        if (!(key instanceof Serializable)) {
            throw new RuntimeException("Instance is not Serializable");
        }
        if (key.getX() != null) {
            throw new RuntimeException("Default constructor should leave private value x unset (null)");
        }

        DSAPrivateKey another = new DSAPrivateKey();
        if (another == key) {
            throw new RuntimeException("Each constructor call must create a new instance");
        }
        if (another.getX() != null) {
            throw new RuntimeException("Second default instance should also have x unset (null)");
        }
    }
}
