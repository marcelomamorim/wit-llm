/*
 * @test
 * @summary Test nextProbablePrime method of BigInteger for expected behavior and exception
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testNextProbablePrimeNegative();
        testNextProbablePrimeZeroAndOne();
        testNextProbablePrimeSmallNumbers();
        testNextProbablePrimeLargeNumber();
    }

    private static void testNextProbablePrimeNegative() {
        try {
            BigInteger neg = BigInteger.valueOf(-1);
            neg.nextProbablePrime();
            throw new RuntimeException("Expected ArithmeticException for negative input");
        } catch (ArithmeticException e) {
            // Expected
        }
    }

    private static void testNextProbablePrimeZeroAndOne() {
        BigInteger zero = BigInteger.ZERO;
        BigInteger one = BigInteger.ONE;
        BigInteger two = BigInteger.valueOf(2);

        if (!zero.nextProbablePrime().equals(two)) {
            throw new RuntimeException("nextProbablePrime of 0 should be 2");
        }
        if (!one.nextProbablePrime().equals(two)) {
            throw new RuntimeException("nextProbablePrime of 1 should be 2");
        }
    }

    private static void testNextProbablePrimeSmallNumbers() {
        // Test next probable prime for small numbers > 1
        BigInteger three = BigInteger.valueOf(3);
        BigInteger four = BigInteger.valueOf(4);
        BigInteger five = BigInteger.valueOf(5);

        if (!three.nextProbablePrime().equals(five)) {
            throw new RuntimeException("nextProbablePrime of 3 should be 5");
        }
        if (!four.nextProbablePrime().equals(five)) {
            throw new RuntimeException("nextProbablePrime of 4 should be 5");
        }
    }

    private static void testNextProbablePrimeLargeNumber() {
        // Test next probable prime for a larger number
        BigInteger large = new BigInteger("1000");
        BigInteger prime = large.nextProbablePrime();
        if (prime.compareTo(large) <= 0) {
            throw new RuntimeException("nextProbablePrime should be greater than input");
        }
        // Check that prime is probably prime with certainty 100
        if (!prime.isProbablePrime(100)) {
            throw new RuntimeException("Returned number should be probably prime");
        }
    }
}
