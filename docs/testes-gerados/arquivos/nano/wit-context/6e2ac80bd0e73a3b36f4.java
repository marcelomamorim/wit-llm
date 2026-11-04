/* @test    @summary Test for KeyStore.PrivateKeyEntry constructor with null parameters and valid parameters    @run main KeyStoreWitupTest */

import java.security.*;
import java.security.cert.Certificate;
import java.util.Collections;

public class KeyStoreWitupTest {

    public static void main(String[] args) {
        testPrivateKeyEntryWithNulls();
        testPrivateKeyEntryWithEmptyChain();
    }

    private static void testPrivateKeyEntryWithNulls() {
        PrivateKey key = null;
        Certificate[] chain = null;
        try {
            new KeyStore.PrivateKeyEntry(key, chain);
            // Expecting NullPointerException due to null privateKey or chain
            System.err.println("Failed: Expected NullPointerException for null privateKey or chain");
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }

    private static void testPrivateKeyEntryWithEmptyChain() {
        PrivateKey key = new DummyPrivateKey();
        Certificate[] chain = new Certificate[0];
        try {
            new KeyStore.PrivateKeyEntry(key, chain);
            // Should succeed, as chain length == 0 is valid
            System.out.println("Passed: PrivateKeyEntry with empty chain");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }

    private static class DummyPrivateKey implements PrivateKey {
        public String getAlgorithm() { return "RSA"; }
        public String getFormat() { return "PKCS#8"; }
        public byte[] getEncoded() { return new byte[0]; }
    }
}
