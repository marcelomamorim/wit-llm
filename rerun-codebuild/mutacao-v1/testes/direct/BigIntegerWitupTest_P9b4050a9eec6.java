/* @test    @summary Test for BigInteger.modInverse to verify correct behavior and exception handling    @run main BigIntegerWitupTest_P9b4050a9eec6 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P9b4050a9eec6 {

    public static void main(String[] args) {
        testModInverseWithPositiveModulus();
        testModInverseWithZeroModulus();
        testModInverseWithNegativeModulus();
        testModInverseWithUnityModulus();
        testModInverseWithNegativeNumber();
        System.out.println("All tests passed.");
    }

    private static void testModInverseWithPositiveModulus() {
        BigInteger a = new BigInteger("42");
        BigInteger m = new BigInteger("2017");
        BigInteger inv = a.modInverse(m);
        BigInteger check = a.multiply(inv).mod(m);
        if (!check.equals(BigInteger.ONE)) {
            throw new AssertionError("Failed: modInverse with positive modulus") ;
        }
    }

    private static void testModInverseWithZeroModulus() {
        BigInteger a = new BigInteger("42");
        BigInteger m = BigInteger.ZERO;
        try {
            a.modInverse(m);
            throw new AssertionError("Expected ArithmeticException for zero modulus") ;
        } catch (ArithmeticException e) {
            // expected
        }
    }

    private static void testModInverseWithNegativeModulus() {
        BigInteger a = new BigInteger("42");
        BigInteger m = new BigInteger("-2017");
        try {
            a.modInverse(m);
            throw new AssertionError("Expected ArithmeticException for negative modulus") ;
        } catch (ArithmeticException e) {
            // expected
        }
    }

    private static void testModInverseWithUnityModulus() {
        BigInteger a = new BigInteger("42");
        BigInteger m = BigInteger.ONE;
        try {
            a.modInverse(m);
            throw new AssertionError("Expected ArithmeticException for modulus 1") ;
        } catch (ArithmeticException e) {
            // expected
        }
    }

    private static void testModInverseWithNegativeNumber() {
        BigInteger a = new BigInteger("-42");
        BigInteger m = new BigInteger("2017");
        BigInteger inv = a.modInverse(m);
        BigInteger check = a.multiply(inv).mod(m);
        if (!check.equals(BigInteger.ONE)) {
            throw new AssertionError("Failed: modInverse with negative number") ;
        }
    }
}
