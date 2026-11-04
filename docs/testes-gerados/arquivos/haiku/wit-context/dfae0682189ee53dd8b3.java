/*
 * @test
 * @summary Witup-generated tests for BigInteger.mod(BigInteger)
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testZeroModulusThrows();
        testNegativeModulusThrows();
        testPositiveResults();
        testNegativeDividendNonNegativeResult();
        testModulusOne();
        testLargeValueInvariant();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testZeroModulusThrows() {
        BigInteger x = BigInteger.valueOf(10);
        try {
            x.mod(BigInteger.ZERO);
        } catch (ArithmeticException e) {
            return;
        }
        throw new RuntimeException("expected ArithmeticException for zero modulus");
    }

    static void testNegativeModulusThrows() {
        BigInteger x = BigInteger.valueOf(10);
        BigInteger m = BigInteger.valueOf(-3);
        try {
            x.mod(m);
        } catch (ArithmeticException e) {
            return;
        }
        throw new RuntimeException("expected ArithmeticException for negative modulus");
    }

    static void testPositiveResults() {
        check(BigInteger.valueOf(7).mod(BigInteger.valueOf(3)).equals(BigInteger.valueOf(1)),
              "7 mod 3 should be 1");
        check(BigInteger.valueOf(-7).mod(BigInteger.valueOf(3)).equals(BigInteger.valueOf(2)),
              "-7 mod 3 should be 2");
        check(BigInteger.valueOf(-6).mod(BigInteger.valueOf(3)).equals(BigInteger.ZERO),
              "-6 mod 3 should be 0");
        check(BigInteger.valueOf(6).mod(BigInteger.valueOf(3)).equals(BigInteger.ZERO),
              "6 mod 3 should be 0");
    }

    static void testNegativeDividendNonNegativeResult() {
        BigInteger m = BigInteger.valueOf(5);
        for (long v = -20; v <= 20; v++) {
            BigInteger r = BigInteger.valueOf(v).mod(m);
            check(r.signum() >= 0, "result must be non-negative for " + v);
            check(r.compareTo(m) < 0, "result must be less than modulus for " + v);
        }
    }

    static void testModulusOne() {
        check(BigInteger.valueOf(-123).mod(BigInteger.ONE).equals(BigInteger.ZERO),
              "any value mod 1 should be 0");
        check(BigInteger.valueOf(123).mod(BigInteger.ONE).equals(BigInteger.ZERO),
              "any value mod 1 should be 0 (positive)");
    }

    static void testLargeValueInvariant() {
        BigInteger x = new BigInteger("-123456789012345678901234567890");
        BigInteger m = BigInteger.valueOf(97);
        BigInteger r = x.mod(m);
        check(r.signum() >= 0, "large negative mod must be non-negative");
        check(r.compareTo(m) < 0, "large negative mod must be less than modulus");
        check(x.subtract(r).remainder(m).signum() == 0,
              "x - r must be divisible by modulus");
    }
}
