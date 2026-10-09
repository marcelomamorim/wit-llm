/* @test    @summary Test BigInteger.nextProbablePrime() method for positive inputs and edge cases    @run main BigIntegerWitupTest_P50a9517b8e20 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P50a9517b8e20 {

    public static void main(String[] args) {
        testNextProbablePrimeWithPositiveNumber();
        testNextProbablePrimeWithZero();
        testNextProbablePrimeWithOne();
        testNextProbablePrimeWithNegative();
        testNextProbablePrimeSmallPrime();
        testNextProbablePrimeLargeNumber();
    }

    static void testNextProbablePrimeWithPositiveNumber() {
        BigInteger bi = BigInteger.valueOf(17);
        BigInteger prime = bi.nextProbablePrime();
        assert prime != null : "Prime should not be null";
        assert prime.isProbablePrime(10) : "Result should be a probable prime";
        assert prime.compareTo(bi) > 0 : "Next probable prime should be greater than original";
    }

    static void testNextProbablePrimeWithZero() {
        BigInteger bi = BigInteger.ZERO;
        BigInteger prime = bi.nextProbablePrime();
        assert prime != null : "Prime should not be null";
        assert prime.equals(BigInteger.TWO) : "Next prime from 0 should be 2";
    }

    static void testNextProbablePrimeWithOne() {
        BigInteger bi = BigInteger.ONE;
        BigInteger prime = bi.nextProbablePrime();
        assert prime != null : "Prime should not be null";
        assert prime.equals(BigInteger.TWO) : "Next prime from 1 should be 2";
    }

    static void testNextProbablePrimeWithNegative() {
        BigInteger bi = BigInteger.valueOf(-5);
        try {
            bi.nextProbablePrime();
            assert false : "Expected ArithmeticException for negative input";
        } catch (ArithmeticException e) {
            assert e.getMessage().contains("start < 0") : "Exception message should mention start < 0";
        }
    }

    static void testNextProbablePrimeSmallPrime() {
        BigInteger bi = BigInteger.valueOf(2);
        BigInteger result = bi.nextProbablePrime();
        assert result != null : "Prime should not be null";
        assert result.equals(BigInteger.TWO) : "Next probable prime from 2 should be 2";
    }

    static void testNextProbablePrimeLargeNumber() {
        BigInteger bi = new BigInteger("123456789012345678901234567890");
        BigInteger prime = bi.nextProbablePrime();
        assert prime != null : "Prime should not be null";
        assert prime.isProbablePrime(10) : "Result should be a probable prime";
        assert prime.compareTo(bi) > 0 : "Next probable prime should be greater than original";
    }
}
