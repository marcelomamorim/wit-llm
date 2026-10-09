/*
 * @test
 * @summary Test BigInteger.modInverse(BigInteger) method behavior
 * @run main BigIntegerWitupTest_P4658fa29a7df
 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P4658fa29a7df {
    public static void main(String[] args) {
        testModInverseWithNonPositiveModulus();
        testModInverseWithModOne();
        testModInverseWithValueOne();
        testModInverseWithTypicalValues();
    }

    private static void testModInverseWithNonPositiveModulus() {
        try {
            BigInteger val = BigInteger.valueOf(10);
            BigInteger zero = BigInteger.ZERO;
            val.modInverse(zero);
            throw new RuntimeException("Expected ArithmeticException for modulus zero");
        } catch (ArithmeticException e) {
            if (!"BigInteger: modulus not positive".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
        try {
            BigInteger val = BigInteger.valueOf(10);
            BigInteger neg = BigInteger.valueOf(-5);
            val.modInverse(neg);
            throw new RuntimeException("Expected ArithmeticException for negative modulus");
        } catch (ArithmeticException e) {
            if (!"BigInteger: modulus not positive".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testModInverseWithModOne() {
        BigInteger val = BigInteger.valueOf(12345);
        BigInteger one = BigInteger.ONE;
        BigInteger result = val.modInverse(one);
        if (!BigInteger.ZERO.equals(result)) {
            throw new RuntimeException("modInverse with modulus 1 should return 0");
        }
    }

    private static void testModInverseWithValueOne() {
        BigInteger one = BigInteger.ONE;
        BigInteger mod = BigInteger.valueOf(7);
        BigInteger result = one.modInverse(mod);
        if (!BigInteger.ONE.equals(result)) {
            throw new RuntimeException("modInverse of 1 mod 7 should be 1");
        }
    }

    private static void testModInverseWithTypicalValues() {
        // Test modInverse for a few known values
        BigInteger val = BigInteger.valueOf(3);
        BigInteger mod = BigInteger.valueOf(11);
        BigInteger inv = val.modInverse(mod);
        // 3 * inv mod 11 should be 1
        BigInteger check = val.multiply(inv).mod(mod);
        if (!BigInteger.ONE.equals(check)) {
            throw new RuntimeException("modInverse failed for 3 mod 11");
        }

        // Test with negative value
        BigInteger negVal = BigInteger.valueOf(-3);
        BigInteger invNeg = negVal.modInverse(mod);
        BigInteger checkNeg = negVal.multiply(invNeg).mod(mod);
        if (!BigInteger.ONE.equals(checkNeg)) {
            throw new RuntimeException("modInverse failed for -3 mod 11");
        }

        // Test with value larger than modulus
        BigInteger largeVal = BigInteger.valueOf(25);
        BigInteger invLarge = largeVal.modInverse(mod);
        BigInteger checkLarge = largeVal.multiply(invLarge).mod(mod);
        if (!BigInteger.ONE.equals(checkLarge)) {
            throw new RuntimeException("modInverse failed for 25 mod 11");
        }
    }
}
