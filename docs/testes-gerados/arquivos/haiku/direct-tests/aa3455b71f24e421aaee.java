/*
 * @test
 * @summary Tests for BigInteger.modInverse observable behavior
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static void testNonPositiveModulusThrows() {
        try {
            BigInteger.valueOf(3).modInverse(BigInteger.ZERO);
            check(false, "modulus zero should throw");
        } catch (ArithmeticException e) {
            // expected
        }
        try {
            BigInteger.valueOf(3).modInverse(BigInteger.valueOf(-7));
            check(false, "negative modulus should throw");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testModulusOneReturnsZero() {
        BigInteger r = BigInteger.valueOf(5).modInverse(BigInteger.ONE);
        check(r.equals(BigInteger.ZERO), "modInverse with m=1 should be 0, got " + r);
    }

    static void testOneReturnsOne() {
        BigInteger r = BigInteger.ONE.modInverse(BigInteger.valueOf(5));
        check(r.equals(BigInteger.ONE), "1.modInverse(5) should be 1, got " + r);
        BigInteger r2 = BigInteger.valueOf(6).modInverse(BigInteger.valueOf(5));
        check(r2.equals(BigInteger.ONE), "6.modInverse(5) should be 1, got " + r2);
    }

    static void testKnownValues() {
        BigInteger r = BigInteger.valueOf(3).modInverse(BigInteger.valueOf(7));
        check(r.equals(BigInteger.valueOf(5)), "3^-1 mod 7 should be 5, got " + r);

        BigInteger n = BigInteger.valueOf(-3).modInverse(BigInteger.valueOf(7));
        check(n.equals(BigInteger.valueOf(2)), "(-3)^-1 mod 7 should be 2, got " + n);
    }

    static void testResultInRangeAndInverse() {
        BigInteger m = new BigInteger("123456789012345678901234567891");
        BigInteger x = new BigInteger("987654321098765432109876543210");
        BigInteger inv = x.modInverse(m);
        check(inv.signum() >= 0, "result must be non-negative");
        check(inv.compareTo(m) < 0, "result must be less than modulus");
        BigInteger prod = x.multiply(inv).mod(m);
        check(prod.equals(BigInteger.ONE), "x * inv mod m should be 1");
    }

    static void testNonInvertibleThrows() {
        try {
            BigInteger.valueOf(2).modInverse(BigInteger.valueOf(4));
            check(false, "2 not invertible mod 4 should throw");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    public static void main(String[] args) throws Exception {
        testNonPositiveModulusThrows();
        testModulusOneReturnsZero();
        testOneReturnsOne();
        testKnownValues();
        testResultInRangeAndInverse();
        testNonInvertibleThrows();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
