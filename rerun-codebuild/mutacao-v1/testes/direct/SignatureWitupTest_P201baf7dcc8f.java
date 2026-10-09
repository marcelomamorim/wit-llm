/* @test    @summary Test Signature.update(ByteBuffer) method for correct behavior and exception handling    @run main SignatureWitupTest_P201baf7dcc8f */

import java.security.*;
import java.nio.ByteBuffer;

public class SignatureWitupTest_P201baf7dcc8f {

    public static void main(String[] args) throws Exception {
        testUpdateWithNullBuffer();
        testUpdateWithUninitializedSignature();
        testUpdateWithValidBuffer();
    }

    private static void testUpdateWithNullBuffer() {
        Signature sig = null;
        try {
            sig = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to get Signature instance", e);
        }
        try {
            sig.update((ByteBuffer) null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (SignatureException e) {
            throw new RuntimeException("Unexpected SignatureException", e);
        }
    }

    private static void testUpdateWithUninitializedSignature() {
        Signature sig = null;
        try {
            sig = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to get Signature instance", e);
        }
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {1, 2, 3});
        try {
            sig.update(buffer);
            throw new AssertionError("Expected SignatureException not thrown");
        } catch (SignatureException e) {
            // Expected exception because signature not initialized
        }
    }

    private static void testUpdateWithValidBuffer() throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        sig.initSign(keyPair.getPrivate());
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {10, 20, 30});
        sig.update(buffer);
        byte[] signatureBytes = sig.sign();
        // Verify signature
        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(keyPair.getPublic());
        verifier.update(buffer);
        boolean verified = verifier.verify(signatureBytes);
        if (!verified) {
            throw new AssertionError("Signature verification failed");
        }
    }
}
