/* @test    @summary Test for BigInteger.modInverse(BigInteger m) method behavior.    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testModInverseWithPositiveModulus();
        testModInverseWithNegativeModulusThrows();
        testModInverseWithOneModulus();
        testModInverseWithZeroModulusThrows();
        testModInverseWithNegativeNumber();
    }

    private static void testModInverseWithPositiveModulus() {
        BigInteger a = new BigInteger("42");
        BigInteger m = new BigInteger("2017");
        BigInteger inv = a.modInverse(m);
        BigInteger check = a.multiply(inv).mod(m);
        if (!check.equals(BigInteger.ONE)) {
            throw new AssertionError("Failed: modInverse with positive modulus. Expected 1, got " + check);
        }
    }

    private static void testModInverseWithNegativeModulusThrows() {
        BigInteger a = new BigInteger("42");
        BigInteger m = new BigInteger("-2017");
        boolean thrown = false;
        try {
            a.modInverse(m);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Failed: modInverse with negative modulus did not throw.");
        }
    }

    private static void testModInverseWithOneModulus() {
        BigInteger a = new BigInteger("42");
        BigInteger m = BigInteger.ONE;
        BigInteger inv = a.modInverse(m);
        if (!inv.equals(BigInteger.ZERO)) {
            throw new AssertionError("Failed: modInverse with modulus 1. Expected 0, got " + inv);
        }
    }

    private static void testModInverseWithZeroModulusThrows() {
        BigInteger a = new BigInteger("42");
        BigInteger m = BigInteger.ZERO;
        boolean thrown = false;
        try {
            a.modInverse(m);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Failed: modInverse with zero modulus did not throw.");
        }
    }

    private static void testModInverseWithNegativeNumber() {
        BigInteger a = new BigInteger("-42");
        BigInteger m = new BigInteger("2017");
        BigInteger inv = a.modInverse(m);
        BigInteger check = a.multiply(inv).mod(m);
        if (!check.equals(BigInteger.ONE)) {
            throw new AssertionError("Failed: modInverse with negative number. Expected 1, got " + check);
        }
    }
}
