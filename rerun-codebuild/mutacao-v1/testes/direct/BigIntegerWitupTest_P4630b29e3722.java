/* @test    @summary Test probablePrime method for valid and invalid bitLength values    @run main BigIntegerWitupTest_P4630b29e3722 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest_P4630b29e3722 {

    public static void main(String[] args) {
        testProbablePrimeValid();
        testProbablePrimeInvalid();
    }

    static void testProbablePrimeValid() {
        Random rnd = new Random(12345);
        int[] bitLengths = {2, 16, 32, 64, 128};
        for (int bitLength : bitLengths) {
            BigInteger primeCandidate = null;
            try {
                primeCandidate = BigInteger.probablePrime(bitLength, rnd);
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception for bitLength=" + bitLength, e);
            }
            if (primeCandidate == null) {
                throw new AssertionError("probablePrime returned null for bitLength=" + bitLength);
            }
            // Check that the bit length is as expected or greater (since probable prime may be slightly larger)
            int actualBitLength = primeCandidate.bitLength();
            if (actualBitLength < bitLength) {
                throw new AssertionError("Generated prime has smaller bit length: expected >= " + bitLength + ", got " + actualBitLength);
            }
            // Check that the number is probably prime (probability test)
            if (!isProbablePrime(primeCandidate, 10)) {
                throw new AssertionError("Number is not probably prime: " + primeCandidate);
            }
        }
    }

    static void testProbablePrimeInvalid() {
        int[] invalidBitLengths = {0, 1, -5};
        for (int invalidBitLength : invalidBitLengths) {
            try {
                BigInteger.probablePrime(invalidBitLength, new Random());
                throw new AssertionError("Expected ArithmeticException for bitLength=" + invalidBitLength);
            } catch (ArithmeticException e) {
                // Expected exception
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception type for bitLength=" + invalidBitLength, e);
            }
        }
    }

    static boolean isProbablePrime(BigInteger candidate, int certainty) {
        // Use the built-in method for testing primality
        // Since the source code is not provided, assume the method exists
        // For JDK 11, BigInteger has isProbablePrime(int certainty)
        return candidate.isProbablePrime(certainty);
    }
}
