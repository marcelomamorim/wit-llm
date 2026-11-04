/*
 * @test
 * @summary Witup-generated behavioral tests for the ECFieldFp(BigInteger p) constructor
 * @run main ECFieldFpWitupTest
 */

import java.math.BigInteger;
import java.security.spec.ECFieldFp;

public class ECFieldFpWitupTest {

    public static void main(String[] args) {
        testPositivePrimeAccepted();
        testOnePositiveAccepted();
        testLargePrimeFieldSize();
        testEqualityAndHashCode();
        testZeroRejected();
        testNegativeRejected();
        testNullRejected();
    }

    static void testPositivePrimeAccepted() {
        BigInteger p = BigInteger.valueOf(23);
        ECFieldFp f = new ECFieldFp(p);
        if (!f.getP().equals(p)) {
            throw new RuntimeException("getP() mismatch for 23");
        }
        if (f.getFieldSize() != 5) {
            throw new RuntimeException("getFieldSize() expected 5 but was " + f.getFieldSize());
        }
    }

    static void testOnePositiveAccepted() {
        ECFieldFp f = new ECFieldFp(BigInteger.ONE);
        if (!f.getP().equals(BigInteger.ONE)) {
            throw new RuntimeException("getP() mismatch for 1");
        }
        if (f.getFieldSize() != 1) {
            throw new RuntimeException("getFieldSize() expected 1 but was " + f.getFieldSize());
        }
    }

    static void testLargePrimeFieldSize() {
        BigInteger p = BigInteger.ONE.shiftLeft(127).subtract(BigInteger.ONE);
        ECFieldFp f = new ECFieldFp(p);
        if (f.getFieldSize() != 127) {
            throw new RuntimeException("getFieldSize() expected 127 but was " + f.getFieldSize());
        }
        if (!f.getP().equals(p)) {
            throw new RuntimeException("getP() mismatch for large prime");
        }
    }

    static void testEqualityAndHashCode() {
        ECFieldFp a = new ECFieldFp(BigInteger.valueOf(23));
        ECFieldFp b = new ECFieldFp(BigInteger.valueOf(23));
        ECFieldFp c = new ECFieldFp(BigInteger.valueOf(29));
        if (!a.equals(b) || a.hashCode() != b.hashCode()) {
            throw new RuntimeException("equal primes must be equal with same hashCode");
        }
        if (a.equals(c)) {
            throw new RuntimeException("different primes must not be equal");
        }
    }

    static void testZeroRejected() {
        try {
            new ECFieldFp(BigInteger.ZERO);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for p = 0");
    }

    static void testNegativeRejected() {
        try {
            new ECFieldFp(BigInteger.valueOf(-7));
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for p = -7");
    }

    static void testNullRejected() {
        try {
            new ECFieldFp(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for p = null");
    }
}
