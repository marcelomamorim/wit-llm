/*
 * @test
 * @summary Test BigInteger.probablePrime method for valid and invalid inputs
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testProbablePrimeThrowsForBitLengthLessThanTwo();
        testProbablePrimeReturnsPrimeOfSmallBitLength();
        testProbablePrimeReturnsPrimeOfLargeBitLength();
    }

    private static void testProbablePrimeThrowsForBitLengthLessThanTwo() {
        Random rnd = new Random(12345L);
        try {
            BigInteger.probablePrime(1, rnd);
            throw new RuntimeException("Expected ArithmeticException for bitLength < 2");
        } catch (ArithmeticException e) {
            if (!"bitLength < 2".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
        try {
            BigInteger.probablePrime(0, rnd);
            throw new RuntimeException("Expected ArithmeticException for bitLength < 2");
        } catch (ArithmeticException e) {
            if (!"bitLength < 2".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
        try {
            BigInteger.probablePrime(-5, rnd);
            throw new RuntimeException("Expected ArithmeticException for bitLength < 2");
        } catch (ArithmeticException e) {
            if (!"bitLength < 2".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testProbablePrimeReturnsPrimeOfSmallBitLength() {
        Random rnd = new Random(12345L);
        int bitLength = 10; // less than SMALL_PRIME_THRESHOLD (which is 95 in OpenJDK 11 source)
        BigInteger prime = BigInteger.probablePrime(bitLength, rnd);
        if (prime.bitLength() != bitLength) {
            throw new RuntimeException("Prime bit length mismatch: expected " + bitLength + " but got " + prime.bitLength());
        }
        if (!prime.isProbablePrime(BigInteger.DEFAULT_PRIME_CERTAINTY)) {
            throw new RuntimeException("Returned number is not prime with default certainty");
        }
    }

    private static void testProbablePrimeReturnsPrimeOfLargeBitLength() {
        Random rnd = new Random(12345L);
        int bitLength = 128; // greater than or equal to SMALL_PRIME_THRESHOLD
        BigInteger prime = BigInteger.probablePrime(bitLength, rnd);
        if (prime.bitLength() != bitLength) {
            throw new RuntimeException("Prime bit length mismatch: expected " + bitLength + " but got " + prime.bitLength());
        }
        if (!prime.isProbablePrime(BigInteger.DEFAULT_PRIME_CERTAINTY)) {
            throw new RuntimeException("Returned number is not prime with default certainty");
        }
    }
}