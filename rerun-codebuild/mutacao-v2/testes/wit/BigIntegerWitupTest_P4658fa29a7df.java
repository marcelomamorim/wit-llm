/*
 * @test
 * @summary Test BigInteger.modInverse(BigInteger) for valid and invalid modulus
 * @run main BigIntegerWitupTest_P4658fa29a7df
 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P4658fa29a7df {
    public static void main(String[] args) {
        testModInverseWithNonPositiveModulus();
        testModInverseWithModOne();
        testModInverseWithNegativeThis();
        testModInverseWithThisGreaterThanModulus();
        testModInverseWithOneModVal();
    }

    private static void testModInverseWithNonPositiveModulus() {
        try {
            BigInteger zero = BigInteger.ZERO;
            zero.modInverse(BigInteger.ZERO);
            throw new RuntimeException("Expected ArithmeticException for modulus zero");
        } catch (ArithmeticException e) {
            // expected
        }
        try {
            BigInteger neg = BigInteger.valueOf(-1);
            BigInteger pos = BigInteger.valueOf(5);
            pos.modInverse(neg);
            throw new RuntimeException("Expected ArithmeticException for negative modulus");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    private static void testModInverseWithModOne() {
        BigInteger any = BigInteger.valueOf(12345);
        BigInteger result = any.modInverse(BigInteger.ONE);
        if (!BigInteger.ZERO.equals(result)) {
            throw new RuntimeException("modInverse with modulus 1 should return 0");
        }
    }

    private static void testModInverseWithNegativeThis() {
        BigInteger negative = BigInteger.valueOf(-3);
        BigInteger modulus = BigInteger.valueOf(11);
        BigInteger inv = negative.modInverse(modulus);
        // Check that (negative * inv) mod modulus == 1
        BigInteger check = negative.multiply(inv).mod(modulus);
        if (!BigInteger.ONE.equals(check)) {
            throw new RuntimeException("modInverse failed for negative this");
        }
    }

    private static void testModInverseWithThisGreaterThanModulus() {
        BigInteger big = BigInteger.valueOf(20);
        BigInteger mod = BigInteger.valueOf(7);
        BigInteger inv = big.modInverse(mod);
        // Check that (big * inv) mod mod == 1
        BigInteger check = big.multiply(inv).mod(mod);
        if (!BigInteger.ONE.equals(check)) {
            throw new RuntimeException("modInverse failed for this >= modulus");
        }
    }

    private static void testModInverseWithOneModVal() {
        BigInteger one = BigInteger.ONE;
        BigInteger mod = BigInteger.valueOf(13);
        BigInteger inv = one.modInverse(mod);
        if (!BigInteger.ONE.equals(inv)) {
            throw new RuntimeException("modInverse of 1 should be 1");
        }
    }
}
