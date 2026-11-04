/* @test    @summary Test Signature.update(byte[] data) with valid data    @run main SignatureWitupTest */

import java.security.*;
import java.util.*;

public class SignatureWitupTest {

    public static void main(String[] args) throws Exception {
        testUpdateWithValidData();
        testUpdateWithEmptyArray();
        testUpdateWithNullArray();
    }

    private static void testUpdateWithValidData() throws Exception {
        String algorithm = "SHA256withRSA";
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        Signature sig = Signature.getInstance(algorithm);
        sig.initSign(kp.getPrivate());
        byte[] data = {1, 2, 3, 4, 5};
        sig.update(data);
        byte[] signatureBytes = sig.sign();
        Signature verifySig = Signature.getInstance(algorithm);
        verifySig.initVerify(kp.getPublic());
        verifySig.update(data);
        boolean verified = verifySig.verify(signatureBytes);
        if (!verified) {
            throw new AssertionError("Signature verification failed for valid data.");
        }
    }

    private static void testUpdateWithEmptyArray() throws Exception {
        String algorithm = "SHA256withRSA";
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        Signature sig = Signature.getInstance(algorithm);
        sig.initSign(kp.getPrivate());
        byte[] emptyData = {};
        sig.update(emptyData);
        byte[] signatureBytes = sig.sign();
        Signature verifySig = Signature.getInstance(algorithm);
        verifySig.initVerify(kp.getPublic());
        verifySig.update(emptyData);
        boolean verified = verifySig.verify(signatureBytes);
        if (!verified) {
            throw new AssertionError("Signature verification failed for empty data.");
        }
    }

    private static void testUpdateWithNullArray() throws Exception {
        String algorithm = "SHA256withRSA";
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        Signature sig = Signature.getInstance(algorithm);
        sig.initSign(kp.getPrivate());
        try {
            sig.update((byte[]) null);
            throw new AssertionError("Expected SignatureException for null data.");
        } catch (SignatureException e) {
            // Expected exception
        }
    }
}
