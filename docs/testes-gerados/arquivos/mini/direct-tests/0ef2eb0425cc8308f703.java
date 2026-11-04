/*
 * @test
 * @summary Test KeyPairGenerator.initialize(int, SecureRandom) behavior
 * @run main KeyPairGeneratorWitupTest
 */

import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class KeyPairGeneratorWitupTest {
    public static void main(String[] args) throws Exception {
        // Test 1: Basic initialize with valid keysize and SecureRandom
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = new SecureRandom();
        kpg.initialize(512, random);

        // Test 2: Initialize with keysize and null SecureRandom (should use default randomness)
        kpg.initialize(1024, null);

        // Test 3: Initialize with a different keysize
        kpg.initialize(2048, random);

        // Test 4: Initialize with zero keysize (some providers may throw, but standard SunRSA does not)
        // We test that either it succeeds or throws RuntimeException
        try {
            kpg.initialize(0, random);
        } catch (RuntimeException e) {
            // Expected possible failure
        }

        // Test 5: Initialize with negative keysize (expect RuntimeException)
        boolean threw = false;
        try {
            kpg.initialize(-1, random);
        } catch (RuntimeException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected RuntimeException for negative keysize");
        }
    }
}
