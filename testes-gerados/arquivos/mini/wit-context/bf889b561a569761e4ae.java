/*
 * @test
 * @summary Test BigInteger(int bitLength, int certainty, Random rnd) constructor for valid and invalid inputs
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testConstructorThrowsForBitLengthLessThanTwo();
        testConstructorGeneratesPrimeForValidBitLength();
    }

    private static void testConstructorThrowsForBitLengthLessThanTwo() {
        Random rnd = new Random(12345L);
        try {
            new BigInteger(1, 10, rnd);
            throw new RuntimeException("Expected ArithmeticException for bitLength < 2");
        } catch (ArithmeticException e) {
            if (!"bitLength < 2".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
        try {
            new BigInteger(0, 10, rnd);
            throw new RuntimeException("Expected ArithmeticException for bitLength < 2");
        } catch (ArithmeticException e) {
            if (!"bitLength < 2".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
        try {
            new BigInteger(-5, 10, rnd);
            throw new RuntimeException("Expected ArithmeticException for bitLength < 2");
        } catch (ArithmeticException e) {
            if (!"bitLength < 2".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testConstructorGeneratesPrimeForValidBitLength() {
        Random rnd = new Random(12345L);
        int bitLength = 10;
        int certainty = 10;
        BigInteger prime = new BigInteger(bitLength, certainty, rnd);
        if (prime == null) {
            throw new RuntimeException("Constructor returned null");
        }
        if (prime.signum() != 1) {
            throw new RuntimeException("Prime signum should be 1 but was " + prime.signum());
        }
        if (prime.bitLength() != bitLength) {
            throw new RuntimeException("Prime bitLength should be " + bitLength + " but was " + prime.bitLength());
        }
        if (!prime.isProbablePrime(certainty)) {
            throw new RuntimeException("Generated number is not prime with certainty " + certainty);
        }
    }
}
