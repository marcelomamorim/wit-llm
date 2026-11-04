/* @test    @summary Test initialize method of KeyPairGenerator with valid keysize and SecureRandom    @run main KeyPairGeneratorWitupTest */

import java.security.*;

public class KeyPairGeneratorWitupTest {

    public static void main(String[] args) {
        testInitializeWithValidParameters();
    }

    public static void testInitializeWithValidParameters() {
        try {
            // Obtain an instance of KeyPairGenerator for RSA
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            // Create a SecureRandom instance
            SecureRandom random = new SecureRandom();
            // Initialize with keysize 2048 and the SecureRandom instance
            kpg.initialize(2048, random);
            // Generate a key pair to verify initialization
            KeyPair keyPair = kpg.generateKeyPair();
            // Assert that the key pair is not null
            if (keyPair == null) {
                throw new AssertionError("Generated KeyPair is null");
            }
            // Assert that public and private keys are not null
            if (keyPair.getPublic() == null || keyPair.getPrivate() == null) {
                throw new AssertionError("Public or Private key is null");
            }
            System.out.println("testInitializeWithValidParameters passed.");
        } catch (Exception e) {
            throw new RuntimeException("Exception during testInitializeWithValidParameters", e);
        }
    }
}
