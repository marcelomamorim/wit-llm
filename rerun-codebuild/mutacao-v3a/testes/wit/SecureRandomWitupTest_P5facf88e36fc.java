/* @test    @summary Test generateSeed with valid and invalid inputs    @run main SecureRandomWitupTest_P5facf88e36fc */

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;

public class SecureRandomWitupTest_P5facf88e36fc {

    public static void main(String[] args) {
        testGenerateSeedValid();
        testGenerateSeedNegative();
    }

    public static void testGenerateSeedValid() {
        try {
            SecureRandom sr = new SecureRandom();
            byte[] seed = sr.generateSeed(16);
            if (seed == null || seed.length != 16) {
                throw new AssertionError("generateSeed should return byte array of length 16");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid generateSeed: " + e);
        }
    }

    public static void testGenerateSeedNegative() {
        try {
            SecureRandom sr = new SecureRandom();
            sr.generateSeed(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative seed length");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
