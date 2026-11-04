/*
 * @test
 * @summary Test BigInteger(int bitLength, int certainty, Random rnd) constructor behavior
 * @run main java.math.BigIntegerWitupTest
 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testBitLengthLessThanTwoThrows();
        testSmallPrimeGeneration();
        testLargePrimeGeneration();
    }

    private static void testBitLengthLessThanTwoThrows() {
        try {
            new BigInteger(1, 10, new Random());
            throw new RuntimeException("Expected ArithmeticException for bitLength < 2");
        } catch (ArithmeticException e) {
            if (!"bitLength < 2".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testSmallPrimeGeneration() {
        // bitLength < SMALL_PRIME_THRESHOLD triggers smallPrime method
        // SMALL_PRIME_THRESHOLD is not public, but from source code context it is 95
        // So test with bitLength = 10 (less than 95)
        int bitLength = 10;
        int certainty = 10;
        Random rnd = new Random(12345L);
        BigInteger prime = new BigInteger(bitLength, certainty, rnd);
        if (prime.signum() != 1) {
            throw new RuntimeException("Prime signum should be 1");
        }
        if (prime.bitLength() != bitLength) {
            throw new RuntimeException("Prime bitLength expected: " + bitLength + ", got: " + prime.bitLength());
        }
    }

    private static void testLargePrimeGeneration() {
        // bitLength >= SMALL_PRIME_THRESHOLD triggers largePrime method
        // Use bitLength = 100 (>= 95)
        int bitLength = 100;
        int certainty = 10;
        Random rnd = new Random(54321L);
        BigInteger prime = new BigInteger(bitLength, certainty, rnd);
        if (prime.signum() != 1) {
            throw new RuntimeException("Prime signum should be 1");
        }
        if (prime.bitLength() != bitLength) {
            throw new RuntimeException("Prime bitLength expected: " + bitLength + ", got: " + prime.bitLength());
        }
    }
}
