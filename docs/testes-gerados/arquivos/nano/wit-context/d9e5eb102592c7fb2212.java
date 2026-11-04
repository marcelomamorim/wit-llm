/* @test    @summary Test for java.security.KeyStore.getProtectionParameter() method.    @run main KeyStoreWitupTest */

import java.security.*;

public class KeyStoreWitupTest {

    public static void main(String[] args) {
        testGetProtectionParameterReturnsProtection();
        testGetProtectionParameterNull();
    }

    private static void testGetProtectionParameterReturnsProtection() {
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            // Initialize keystore with default parameters
            ks.load(null, null);
            ProtectionParameter result = ks.getProtectionParameter();
            if (result == null) {
                System.out.println("Test failed: getProtectionParameter() returned null when protection was expected.");
            } else {
                System.out.println("Test passed: getProtectionParameter() returned a ProtectionParameter.");
            }
        } catch (Exception e) {
            System.out.println("Test failed with exception: " + e);
        }
    }

    private static void testGetProtectionParameterNull() {
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            // Do not load keystore, protection should be null
            ProtectionParameter result = ks.getProtectionParameter();
            if (result != null) {
                System.out.println("Test failed: getProtectionParameter() returned non-null when protection was not set.");
            } else {
                System.out.println("Test passed: getProtectionParameter() returned null as expected.");
            }
        } catch (Exception e) {
            System.out.println("Test failed with exception: " + e);
        }
    }
}
