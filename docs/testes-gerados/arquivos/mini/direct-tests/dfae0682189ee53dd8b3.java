/*
 * @test
 * @summary Test java.math.BigInteger.mod(BigInteger) method behavior
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        testModPositiveModulus();
        testModNegativeModulusThrows();
        testModZeroModulusThrows();
        testModResultNonNegative();
    }

    static void testModPositiveModulus() {
        BigInteger a = new BigInteger("123456789");
        BigInteger m = new BigInteger("10000");
        BigInteger mod = a.mod(m);
        // mod must be in [0, m-1]
        if (mod.signum() < 0 || mod.compareTo(m) >= 0) {
            throw new AssertionError("mod result out of range: " + mod);
        }
        // mod should equal a.remainder(m) if remainder >= 0
        BigInteger rem = a.remainder(m);
        if (rem.signum() >= 0 && !mod.equals(rem)) {
            throw new AssertionError("mod result differs from remainder when remainder >= 0");
        }
        // mod should equal remainder + m if remainder < 0
        if (rem.signum() < 0 && !mod.equals(rem.add(m))) {
            throw new AssertionError("mod result differs from remainder + m when remainder < 0");
        }
    }

    static void testModNegativeModulusThrows() {
        BigInteger a = BigInteger.valueOf(10);
        BigInteger m = BigInteger.valueOf(-1);
        try {
            a.mod(m);
            throw new AssertionError("Expected ArithmeticException for negative modulus");
        } catch (ArithmeticException e) {
            if (!"BigInteger: modulus not positive".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    static void testModZeroModulusThrows() {
        BigInteger a = BigInteger.valueOf(10);
        BigInteger m = BigInteger.ZERO;
        try {
            a.mod(m);
            throw new AssertionError("Expected ArithmeticException for zero modulus");
        } catch (ArithmeticException e) {
            if (!"BigInteger: modulus not positive".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    static void testModResultNonNegative() {
        BigInteger a = new BigInteger("-123456789");
        BigInteger m = new BigInteger("10000");
        BigInteger mod = a.mod(m);
        if (mod.signum() < 0) {
            throw new AssertionError("mod result is negative");
        }
        if (mod.compareTo(m) >= 0) {
            throw new AssertionError("mod result >= modulus");
        }
    }
}
