/* @test    @summary Test BigInteger.modInverse for positive modulus and various inputs    @run main BigIntegerWitupTest_Pd6c73aea49aa */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pd6c73aea49aa {

    public static void main(String[] args) {
        testModInverseWithPositiveModulus();
        testModInverseWithZeroModulus();
        testModInverseWithNegativeModulus();
        testModInverseWithOneModulus();
        testModInverseWithNegativeNumber();
        testModInverseWithLargeNumbers();
    }

    static void testModInverseWithPositiveModulus() {
        BigInteger a = new BigInteger("42");
        BigInteger m = new BigInteger("2017");
        BigInteger inv = a.modInverse(m);
        BigInteger check = a.multiply(inv).mod(m);
        if (!check.equals(BigInteger.ONE)) {
            throw new AssertionError("Failed: modInverse with positive modulus");
        }
    }

    static void testModInverseWithZeroModulus() {
        BigInteger a = new BigInteger("42");
        try {
            a.modInverse(BigInteger.ZERO);
            throw new AssertionError("Expected ArithmeticException for zero modulus");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testModInverseWithNegativeModulus() {
        BigInteger a = new BigInteger("42");
        BigInteger m = new BigInteger("-2017");
        try {
            a.modInverse(m);
            throw new AssertionError("Expected ArithmeticException for negative modulus");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testModInverseWithOneModulus() {
        BigInteger a = new BigInteger("42");
        BigInteger one = BigInteger.ONE;
        BigInteger result = a.modInverse(one);
        if (!result.equals(BigInteger.ZERO)) {
            throw new AssertionError("Failed: modInverse with modulus 1");
        }
    }

    static void testModInverseWithNegativeNumber() {
        BigInteger a = new BigInteger("-42");
        BigInteger m = new BigInteger("2017");
        BigInteger inv = a.modInverse(m);
        BigInteger check = a.multiply(inv).mod(m);
        if (!check.equals(BigInteger.ONE)) {
            throw new AssertionError("Failed: modInverse with negative number");
        }
    }

    static void testModInverseWithLargeNumbers() {
        BigInteger a = new BigInteger("123456789123456789123456789");
        BigInteger m = new BigInteger("987654321987654321987654321");
        BigInteger inv = a.modInverse(m);
        BigInteger check = a.multiply(inv).mod(m);
        if (!check.equals(BigInteger.ONE)) {
            throw new AssertionError("Failed: modInverse with large numbers");
        }
    }
}
