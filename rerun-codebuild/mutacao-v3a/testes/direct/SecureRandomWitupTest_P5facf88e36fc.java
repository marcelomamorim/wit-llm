/* @test    @summary Test SecureRandom.generateSeed with valid input    @run main SecureRandomWitupTest_P5facf88e36fc */

import java.security.SecureRandom;
import java.util.Arrays;

public class SecureRandomWitupTest_P5facf88e36fc {

    public static void main(String[] args) {
        testGenerateSeedWithPositiveBytes();
        testGenerateSeedWithZeroBytes();
        testGenerateSeedWithNegativeBytes();
    }

    static void testGenerateSeedWithPositiveBytes() {
        SecureRandom sr = new SecureRandom();
        int numBytes = 16;
        byte[] seed = sr.generateSeed(numBytes);
        assert seed != null : "Seed should not be null";
        assert seed.length == numBytes : "Seed length mismatch";
        System.out.println("testGenerateSeedWithPositiveBytes passed.");
    }

    static void testGenerateSeedWithZeroBytes() {
        SecureRandom sr = new SecureRandom();
        int numBytes = 0;
        byte[] seed = sr.generateSeed(numBytes);
        assert seed != null : "Seed should not be null when zero bytes";
        assert seed.length == 0 : "Seed length should be zero";
        System.out.println("testGenerateSeedWithZeroBytes passed.");
    }

    static void testGenerateSeedWithNegativeBytes() {
        SecureRandom sr = new SecureRandom();
        try {
            sr.generateSeed(-1);
            assert false : "IllegalArgumentException expected";
        } catch (IllegalArgumentException e) {
            System.out.println("testGenerateSeedWithNegativeBytes passed.");
        }
    }
}
