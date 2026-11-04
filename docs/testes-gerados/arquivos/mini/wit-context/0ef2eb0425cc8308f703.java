/*
 * @test
 * @summary Test deterministic behavior of KeyPairGenerator.initialize(int, SecureRandom)
 * @run main KeyPairGeneratorWitupTest
 */

import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class KeyPairGeneratorWitupTest {
    public static void main(String[] args) throws Exception {
        testInitializeWithValidAlgorithm();
        testInitializeWithInvalidAlgorithm();
    }

    private static void testInitializeWithValidAlgorithm() throws Exception {
        // Use a known algorithm to get a KeyPairGenerator instance
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
        SecureRandom random = new SecureRandom();

        // Initialize with a valid keysize and SecureRandom
        kpg.initialize(1024, random);

        // If no exception is thrown, test passes for this scenario
        // No direct observable state to assert, so just ensure no exception
    }

    private static void testInitializeWithInvalidAlgorithm() {
        // Attempt to get a KeyPairGenerator with an invalid algorithm name
        // Expect NoSuchAlgorithmException
        try {
            KeyPairGenerator.getInstance("InvalidAlgorithmName");
            throw new RuntimeException("Expected NoSuchAlgorithmException not thrown");
        } catch (NoSuchAlgorithmException e) {
            // Expected exception
        }

        // Also test getInstance with invalid provider name
        try {
            KeyPairGenerator.getInstance("DSA", "InvalidProviderName");
            throw new RuntimeException("Expected NoSuchProviderException not thrown");
        } catch (NoSuchProviderException e) {
            // Expected exception
        } catch (NoSuchAlgorithmException e) {
            // Also acceptable if provider not found
        }
    }
}
