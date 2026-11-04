/* @test    @summary Test for BigInteger.modInverse(BigInteger m) handling positive modulus and exception cases    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testModInverseWithPositiveModulus();
        testModInverseWithZeroModulus();
        testModInverseWithNegativeModulus();
        testModInverseWithOneModulus();
    }

    private static void testModInverseWithPositiveModulus() {
        BigInteger a = new BigInteger("42");
        BigInteger m = new BigInteger("7");
        try {
            BigInteger inv = a.modInverse(m);
            System.out.println("modInverse with positive modulus: " + inv);
        } catch (ArithmeticException e) {
            System.err.println("Unexpected exception for positive modulus: " + e.getMessage());
        }
    }

    private static void testModInverseWithZeroModulus() {
        BigInteger a = new BigInteger("42");
        BigInteger m = BigInteger.ZERO;
        try {
            a.modInverse(m);
            System.err.println("Expected ArithmeticException not thrown for zero modulus");
        } catch (ArithmeticException e) {
            System.out.println("Correctly caught exception for zero modulus: " + e.getMessage());
        }
    }

    private static void testModInverseWithNegativeModulus() {
        BigInteger a = new BigInteger("42");
        BigInteger m = new BigInteger("-7");
        try {
            a.modInverse(m);
            System.err.println("Expected ArithmeticException not thrown for negative modulus");
        } catch (ArithmeticException e) {
            System.out.println("Correctly caught exception for negative modulus: " + e.getMessage());
        }
    }

    private static void testModInverseWithOneModulus() {
        BigInteger a = new BigInteger("42");
        BigInteger m = BigInteger.ONE;
        try {
            BigInteger inv = a.modInverse(m);
            if (inv.equals(BigInteger.ZERO)) {
                System.out.println("modInverse with modulus 1: 0 (expected)");
            } else {
                System.err.println("Unexpected result for modulus 1: " + inv);
            }
        } catch (ArithmeticException e) {
            System.err.println("Unexpected exception for modulus 1: " + e.getMessage());
        }
    }
}
