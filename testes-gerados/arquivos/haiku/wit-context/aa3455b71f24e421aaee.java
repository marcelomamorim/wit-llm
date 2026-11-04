/*
 * @test
 * @summary WITUP-derived checks for BigInteger.modInverse argument validation and results
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        checkModulusNotPositiveThrows(BigInteger.valueOf(3), BigInteger.valueOf(0));
        checkModulusNotPositiveThrows(BigInteger.valueOf(3), BigInteger.valueOf(-5));
        checkModulusNotPositiveThrows(BigInteger.valueOf(-3), BigInteger.valueOf(-7));

        checkEquals(BigInteger.valueOf(3).modInverse(BigInteger.valueOf(1)), BigInteger.valueOf(0), "modulus one");
        checkEquals(BigInteger.valueOf(1).modInverse(BigInteger.valueOf(5)), BigInteger.valueOf(1), "this is one");

        checkInverse(BigInteger.valueOf(3), BigInteger.valueOf(7), BigInteger.valueOf(5));
        checkInverse(BigInteger.valueOf(10), BigInteger.valueOf(7), BigInteger.valueOf(5));
        checkInverse(BigInteger.valueOf(-3), BigInteger.valueOf(7), BigInteger.valueOf(2));
        checkInverse(new BigInteger("-1"), BigInteger.valueOf(13), BigInteger.valueOf(12));

        checkNotInvertibleThrows(BigInteger.valueOf(2), BigInteger.valueOf(4));
        checkNotInvertibleThrows(BigInteger.valueOf(6), BigInteger.valueOf(9));
    }

    static void checkModulusNotPositiveThrows(BigInteger a, BigInteger m) {
        try {
            a.modInverse(m);
        } catch (ArithmeticException e) {
            return;
        }
        throw new RuntimeException("Expected ArithmeticException for non-positive modulus " + m);
    }

    static void checkNotInvertibleThrows(BigInteger a, BigInteger m) {
        try {
            a.modInverse(m);
        } catch (ArithmeticException e) {
            return;
        }
        throw new RuntimeException("Expected ArithmeticException for non-invertible " + a + " mod " + m);
    }

    static void checkInverse(BigInteger a, BigInteger m, BigInteger expected) {
        BigInteger r = a.modInverse(m);
        checkEquals(r, expected, "inverse of " + a + " mod " + m);
        if (r.signum() < 0 || r.compareTo(m) >= 0) {
            throw new RuntimeException("Result out of range [0, m): " + r);
        }
        if (!a.multiply(r).mod(m).equals(BigInteger.ONE)) {
            throw new RuntimeException("Product is not one mod m for " + a + " mod " + m);
        }
    }

    static void checkEquals(BigInteger actual, BigInteger expected, String what) {
        if (!actual.equals(expected)) {
            throw new RuntimeException(what + ": expected " + expected + " but was " + actual);
        }
    }
}
