/* @test    @summary Test the sign() method of java.security.Signature for correct behavior and exception handling    @run main SignatureWitupTest */

import java.security.*;
import java.util.Arrays;

public class SignatureWitupTest {

    public static void main(String[] args) throws Exception {
        testSignWithInitializedForSign();
        testSignWithoutInitialization();
    }

    private static void testSignWithInitializedForSign() throws Exception {
        // Obtain a Signature instance for a known algorithm
        Signature sig = Signature.getInstance("SHA256withRSA");
        // Generate a key pair for signing
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        // Initialize for signing
        sig.initSign(kp.getPrivate());
        // Update with some data
        byte[] data = "test data".getBytes("UTF-8");
        sig.update(data);
        // Call sign() and verify output is not null and has length
        byte[] signatureBytes = sig.sign();
        if (signatureBytes == null || signatureBytes.length == 0) {
            throw new AssertionError("Signature output is null or empty");
        }
        System.out.println("testSignWithInitializedForSign passed.");
    }

    private static void testSignWithoutInitialization() throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        // Do not initialize
        try {
            sig.sign();
            throw new AssertionError("Expected SignatureException not thrown");
        } catch (SignatureException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not initialized for signing")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
            System.out.println("testSignWithoutInitialization passed.");
        }
    }
}
