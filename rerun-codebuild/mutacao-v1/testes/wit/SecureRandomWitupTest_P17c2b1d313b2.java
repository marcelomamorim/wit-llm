/* @test    @summary Test generateSeed with valid and invalid inputs    @run main SecureRandomWitupTest_P17c2b1d313b2 */

import java.security.SecureRandom;

public class SecureRandomWitupTest_P17c2b1d313b2 {

    public static void main(String[] args) {
        testGenerateSeedValid();
        testGenerateSeedNegative();
    }

    private static void testGenerateSeedValid() {
        try {
            SecureRandom sr = new SecureRandom();
            byte[] seed = sr.generateSeed(10);
            if (seed == null || seed.length != 10) {
                throw new AssertionError("Failed: generateSeed with valid input did not produce expected output");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGenerateSeedValid: " + e);
        }
    }

    private static void testGenerateSeedNegative() {
        try {
            SecureRandom sr = new SecureRandom();
            sr.generateSeed(-1);
            throw new AssertionError("Failed: generateSeed with negative input did not throw exception");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type in testGenerateSeedNegative: " + e);
        }
    }
}
