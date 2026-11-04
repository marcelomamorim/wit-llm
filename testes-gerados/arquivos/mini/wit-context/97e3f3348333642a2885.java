/*
 * @test
 * @summary Test BigInteger.probablePrime behavior including exception for invalid bitLength
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testProbablePrimeValid();
        testProbablePrimeInvalidBitLength();
    }

    private static void testProbablePrimeValid() {
        Random rnd = new Random(12345L);
        int bitLength = 10;
        BigInteger prime = BigInteger.probablePrime(bitLength, rnd);
        if (prime == null) {
            throw new RuntimeException("probablePrime returned null");
        }
        if (prime.bitLength() != bitLength) {
            throw new RuntimeException("probablePrime returned prime with incorrect bitLength: " + prime.bitLength());
        }
        if (!prime.isProbablePrime(BigInteger.DEFAULT_PRIME_CERTAINTY)) {
            throw new RuntimeException("probablePrime returned a non-prime number");
        }
    }

    private static void testProbablePrimeInvalidBitLength() {
        Random rnd = new Random();
        try {
            BigInteger.probablePrime(1, rnd);
            throw new RuntimeException("Expected ArithmeticException for bitLength < 2");
        } catch (ArithmeticException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("bitLength < 2")) {
                throw new RuntimeException("ArithmeticException message unexpected: " + msg);
            }
        }
    }

    // Constant copied from BigInteger source for test consistency
    private static final int DEFAULT_PRIME_CERTAINTY = 100;
}
