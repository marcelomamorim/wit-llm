/* @test    @summary Test for Signature.sign() method behavior in various states    @run main SignatureWitupTest_P7541a7e4796b */

import java.security.*;

public class SignatureWitupTest_P7541a7e4796b {

    public static void main(String[] args) throws Exception {
        testSignWithoutInitialization();
        testSignAfterInitialization();
        testSignAfterUpdate();
    }

    static void testSignWithoutInitialization() {
        Signature sig = Signature.getInstance("SHA256withRSA");
        try {
            sig.sign();
            throw new AssertionError("Expected SignatureException not thrown when signing without init");
        } catch (SignatureException e) {
            // Expected exception
        }
    }

    static void testSignAfterInitialization() throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        sig.initSign(kp.getPrivate());
        byte[] data = {1,2,3,4};
        sig.update(data);
        byte[] signed = sig.sign();
        if (signed == null || signed.length == 0) {
            throw new AssertionError("Signing returned null or empty byte array");
        }
    }

    static void testSignAfterUpdate() throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        sig.initSign(kp.getPrivate());
        sig.update(new byte[] {10, 20});
        byte[] result = sig.sign();
        if (result == null || result.length == 0) {
            throw new AssertionError("Sign after update returned null or empty byte array");
        }
    }
}
