/*
 * @test
 * @summary Test java.math.BigInteger.nextProbablePrime() method behavior
 * @run main java.math.BigIntegerWitupTest
 */

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testNegativeInputThrows();
        testZeroAndOneReturnTwo();
        testSmallNumbersNextPrime();
        testNextPrimeAfterSmallComposite();
        testNextPrimeAfterLargeNumber();
    }

    static void testNegativeInputThrows() {
        try {
            BigInteger neg = BigInteger.valueOf(-1);
            neg.nextProbablePrime();
            throw new RuntimeException("Expected ArithmeticException for negative input");
        } catch (ArithmeticException e) {
            // expected
            if (!e.getMessage().startsWith("start < 0")) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    static void testZeroAndOneReturnTwo() {
        BigInteger zero = BigInteger.ZERO;
        BigInteger one = BigInteger.ONE;
        BigInteger two = BigInteger.valueOf(2);

        BigInteger zeroNext = zero.nextProbablePrime();
        if (!zeroNext.equals(two)) {
            throw new RuntimeException("nextProbablePrime() of 0 should be 2, got " + zeroNext);
        }

        BigInteger oneNext = one.nextProbablePrime();
        if (!oneNext.equals(two)) {
            throw new RuntimeException("nextProbablePrime() of 1 should be 2, got " + oneNext);
        }
    }

    static void testSmallNumbersNextPrime() {
        // Test next prime after 2 is 3
        BigInteger two = BigInteger.valueOf(2);
        BigInteger three = BigInteger.valueOf(3);
        BigInteger next = two.nextProbablePrime();
        if (!next.equals(three)) {
            throw new RuntimeException("nextProbablePrime() of 2 should be 3, got " + next);
        }

        // Test next prime after 3 is 5
        BigInteger five = BigInteger.valueOf(5);
        next = three.nextProbablePrime();
        if (!next.equals(five)) {
            throw new RuntimeException("nextProbablePrime() of 3 should be 5, got " + next);
        }
    }

    static void testNextPrimeAfterSmallComposite() {
        // 9 is composite, next prime is 11
        BigInteger nine = BigInteger.valueOf(9);
        BigInteger eleven = BigInteger.valueOf(11);
        BigInteger next = nine.nextProbablePrime();
        if (!next.equals(eleven)) {
            throw new RuntimeException("nextProbablePrime() of 9 should be 11, got " + next);
        }
    }

    static void testNextPrimeAfterLargeNumber() {
        // Use a large number > SMALL_PRIME_THRESHOLD (which is 30 in BigInteger source)
        // For example, 1 << 31 (2^31) is 2147483648
        BigInteger large = BigInteger.valueOf(1).shiftLeft(31);
        BigInteger prime = large.nextProbablePrime();
        // prime should be > large
        if (prime.compareTo(large) <= 0) {
            throw new RuntimeException("nextProbablePrime() should return prime > input for large input");
        }
        // prime should be probably prime with certainty 100 (default)
        if (!prime.isProbablePrime(100)) {
            throw new RuntimeException("Returned number is not prime with certainty 100");
        }
    }
}
