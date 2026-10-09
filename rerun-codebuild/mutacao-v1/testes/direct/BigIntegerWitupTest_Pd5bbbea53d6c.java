/* @test    @summary Test BigInteger.mod() with positive modulus    @run main BigIntegerWitupTest_Pd5bbbea53d6c */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pd5bbbea53d6c {
    public static void main(String[] args) {
        try {
            testModWithPositiveModulus();
            testModWithNegativeResult();
            testModWithZeroModulus();
            testModWithNegativeModulus();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    static void testModWithPositiveModulus() {
        BigInteger a = new BigInteger("12345678901234567890");
        BigInteger m = new BigInteger("987654321");
        BigInteger result = a.mod(m);
        BigInteger expected = a.remainder(m);
        if (result.signum() < 0) {
            expected = expected.add(m);
        }
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: mod with positive modulus. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testModWithNegativeResult() {
        BigInteger a = new BigInteger("-12345678901234567890");
        BigInteger m = new BigInteger("987654321");
        BigInteger result = a.mod(m);
        BigInteger expected = a.remainder(m);
        if (expected.signum() < 0) {
            expected = expected.add(m);
        }
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: mod with negative a. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testModWithZeroModulus() {
        BigInteger a = new BigInteger("12345");
        try {
            a.mod(BigInteger.ZERO);
            throw new AssertionError("Expected ArithmeticException for zero modulus");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testModWithNegativeModulus() {
        BigInteger a = new BigInteger("12345");
        BigInteger negativeModulus = new BigInteger("-100");
        try {
            a.mod(negativeModulus);
            throw new AssertionError("Expected ArithmeticException for negative modulus");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
