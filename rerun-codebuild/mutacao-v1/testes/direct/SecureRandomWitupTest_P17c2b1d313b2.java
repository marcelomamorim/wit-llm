/* @test    @summary Test SecureRandom.generateSeed(int) with valid and invalid inputs    @run main SecureRandomWitupTest_P17c2b1d313b2 */

import java.security.SecureRandom;

public class SecureRandomWitupTest_P17c2b1d313b2 {

    public static void main(String[] args) {
        testGenerateSeedValid();
        testGenerateSeedNegative();
    }

    static void testGenerateSeedValid() {
        try {
            SecureRandom sr = new SecureRandom();
            byte[] seed = sr.generateSeed(16);
            if (seed == null || seed.length != 16) {
                throw new AssertionError("Expected seed of length 16, but got: " + (seed == null ? "null" : seed.length));
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGenerateSeedValid: " + e);
        }
    }

    static void testGenerateSeedNegative() {
        try {
            SecureRandom sr = new SecureRandom();
            sr.generateSeed(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative seed length");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
