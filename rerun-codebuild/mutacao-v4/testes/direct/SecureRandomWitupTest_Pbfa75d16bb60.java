/* @test
   @summary Test SecureRandom.generateSeed(int) with valid and invalid inputs.
   @run main SecureRandomWitupTest_Pbfa75d16bb60
 */
import java.security.SecureRandom;
import java.util.Arrays;

public class SecureRandomWitupTest_Pbfa75d16bb60 {
    public static void main(String[] args) {
        testGenerateSeedValid();
        testGenerateSeedNegative();
    }

    private static void testGenerateSeedValid() {
        SecureRandom sr = new SecureRandom();
        int seedSize = 16;
        byte[] seed = sr.generateSeed(seedSize);
        if (seed == null) {
            throw new AssertionError("generateSeed returned null");
        }
        if (seed.length != seedSize) {
            throw new AssertionError("Expected seed length " + seedSize + " but got " + seed.length);
        }
        // Generate seed with larger size
        int largerSize = 32;
        byte[] largerSeed = sr.generateSeed(largerSize);
        if (largerSeed == null) {
            throw new AssertionError("generateSeed returned null for larger size");
        }
        if (largerSeed.length != largerSize) {
            throw new AssertionError("Expected seed length " + largerSize + " but got " + largerSeed.length);
        }
        // Check that different calls produce different seeds (not guaranteed but likely)
        if (Arrays.equals(seed, largerSeed)) {
            throw new AssertionError("Seeds should not be equal");
        }
    }

    private static void testGenerateSeedNegative() {
        SecureRandom sr = new SecureRandom();
        boolean exceptionThrown = false;
        try {
            sr.generateSeed(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative input");
        }
    }
}
