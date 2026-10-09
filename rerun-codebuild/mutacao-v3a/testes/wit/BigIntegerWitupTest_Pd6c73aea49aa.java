/* @test    @summary Test for BigInteger.modInverse method, covering normal and exceptional behavior    @run main BigIntegerWitupTest_Pd6c73aea49aa */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pd6c73aea49aa {

    public static void main(String[] args) {
        testModInverseNormal();
        testModInverseInvalid();
        testModInverseOne();
        testModInverseNegative();
    }

    static void testModInverseNormal() {
        BigInteger a = new BigInteger("42");
        BigInteger m = new BigInteger("2017");
        try {
            BigInteger inv = a.modInverse(m);
            // Verify that (a * inv) mod m == 1
            BigInteger product = a.multiply(inv).mod(m);
            if (!product.equals(BigInteger.ONE)) {
                throw new AssertionError("Normal case failed: (a * inv) mod m != 1, got " + product);
            }
        } catch (Exception e) {
            throw new AssertionError("Normal case threw exception: " + e);
        }
    }

    static void testModInverseInvalid() {
        BigInteger a = new BigInteger("42");
        BigInteger invalidM = new BigInteger("0"); // signum=0, should cause exception
        try {
            a.modInverse(invalidM);
            throw new AssertionError("Expected ArithmeticException for non-positive modulus");
        } catch (ArithmeticException e) {
            // Expected exception
            if (!e.getMessage().contains("not positive")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    static void testModInverseOne() {
        BigInteger a = new BigInteger("123456789");
        BigInteger m = BigInteger.ONE;
        try {
            BigInteger res = a.modInverse(m);
            if (!res.equals(BigInteger.ZERO)) {
                throw new AssertionError("modInverse with modulus 1 should return 0, got " + res);
            }
        } catch (Exception e) {
            throw new AssertionError("modInverse with modulus 1 threw exception: " + e);
        }
    }

    static void testModInverseNegative() {
        BigInteger a = new BigInteger("42");
        BigInteger m = new BigInteger("-2017"); // negative modulus, behavior undefined, but should throw
        try {
            a.modInverse(m);
            throw new AssertionError("Expected ArithmeticException for negative modulus");
        } catch (ArithmeticException e) {
            if (!e.getMessage().contains("not positive")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
