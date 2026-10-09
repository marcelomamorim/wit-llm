/*
 * @test
 * @summary Test SecureRandom.generateSeed(int) for valid and invalid inputs
 * @run main SecureRandomWitupTest_P35411cff7934
 */

import java.security.SecureRandom;

public class SecureRandomWitupTest_P35411cff7934 {
    public static void main(String[] args) throws Exception {
        SecureRandom sr = new SecureRandom();

        // Test valid input: generate seed of length 0
        byte[] seed0 = sr.generateSeed(0);
        if (seed0 == null) {
            throw new RuntimeException("generateSeed(0) returned null");
        }

        // Test valid input: generate seed of length 16
        byte[] seed16 = sr.generateSeed(16);
        if (seed16 == null || seed16.length != 16) {
            throw new RuntimeException("generateSeed(16) returned invalid array");
        }

        // Test invalid input: negative length should throw IllegalArgumentException
        boolean threw = false;
        try {
            sr.generateSeed(-1);
        } catch (IllegalArgumentException iae) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("generateSeed(-1) did not throw IllegalArgumentException");
        }
    }
}
