/*
 * @test
 * @summary Test deterministic behavior of KeyStore.getProtectionParameter()
 * @run main KeyStoreWitupTest
 */

import java.security.KeyStore;
import java.security.KeyStore.ProtectionParameter;

public class KeyStoreWitupTest {
    public static void main(String[] args) throws Exception {
        // Obtain a KeyStore instance of default type
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

        // The protection parameter is not set by default, expect null
        ProtectionParameter pp = ks.getProtectionParameter();
        if (pp != null) {
            throw new RuntimeException("Expected null ProtectionParameter but got non-null");
        }

        // Additional check: getProtectionParameter should consistently return the same value
        ProtectionParameter pp2 = ks.getProtectionParameter();
        if (pp2 != pp) {
            throw new RuntimeException("getProtectionParameter returned different instances on repeated calls");
        }

        System.out.println("Test passed: getProtectionParameter returns null as expected and is consistent.");
    }
}
