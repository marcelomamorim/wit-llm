/* @test    @summary Test for BigInteger.nextProbablePrime() method behavior.    @run main BigIntegerWitupTest_Pdbed2e0fd064 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pdbed2e0fd064 {
    public static void main(String[] args) {
        testNextProbablePrimeNegative();
        testNextProbablePrimeZero();
        testNextProbablePrimeOne();
        testNextProbablePrimeSmallNumber();
        testNextProbablePrimeLargeNumber();
        testNextProbablePrimeEdgeCase();
    }

    static void testNextProbablePrimeNegative() {
        try {
            BigInteger bi = new BigInteger("-5");
            bi.nextProbablePrime();
            throw new AssertionError("Expected ArithmeticException for negative input");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }

    static void testNextProbablePrimeZero() {
        BigInteger bi = new BigInteger("0");
        BigInteger result = bi.nextProbablePrime();
        if (!result.equals(BigInteger.TWO)) {
            throw new AssertionError("Expected 2 for input 0, got " + result);
        }
    }

    static void testNextProbablePrimeOne() {
        BigInteger bi = new BigInteger("1");
        BigInteger result = bi.nextProbablePrime();
        if (!result.equals(BigInteger.TWO)) {
            throw new AssertionError("Expected 2 for input 1, got " + result);
        }
    }

    static void testNextProbablePrimeSmallNumber() {
        BigInteger bi = new BigInteger("14");
        BigInteger result = bi.nextProbablePrime();
        if (!result.equals(BigInteger.valueOf(17))) {
            throw new AssertionError("Expected 17 for input 14, got " + result);
        }
    }

    static void testNextProbablePrimeLargeNumber() {
        BigInteger bi = new BigInteger("1000000000000");
        BigInteger result = bi.nextProbablePrime();
        if (result == null) {
            throw new AssertionError("Result should not be null for large number");
        }
        if (!result.isProbablePrime(10)) {
            throw new AssertionError("Result is not prime: " + result);
        }
    }

    static void testNextProbablePrimeEdgeCase() {
        BigInteger bi = new BigInteger("9999999967");
        BigInteger result = bi.nextProbablePrime();
        if (!result.isProbablePrime(10)) {
            throw new AssertionError("Result should be prime: " + result);
        }
    }
}
