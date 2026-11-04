/*
 * @test
 * @summary Test constructor of ECFieldFp for valid and invalid inputs
 * @run main ECFieldFpWitupTest
 */

import java.math.BigInteger;
import java.security.spec.ECFieldFp;

public class ECFieldFpWitupTest {
    public static void main(String[] args) {
        testValidPrime();
        testZeroPrime();
        testNegativePrime();
        testNullPrime();
    }

    private static void testValidPrime() {
        BigInteger prime = new BigInteger("17");
        ECFieldFp field = new ECFieldFp(prime);
        if (!field.getP().equals(prime)) {
            throw new RuntimeException("getP() did not return the expected prime");
        }
        if (field.getFieldSize() != prime.bitLength()) {
            throw new RuntimeException("getFieldSize() did not return the expected bit length");
        }
    }

    private static void testZeroPrime() {
        BigInteger zero = BigInteger.ZERO;
        try {
            new ECFieldFp(zero);
            throw new RuntimeException("Expected IllegalArgumentException for zero prime");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNegativePrime() {
        BigInteger negative = BigInteger.valueOf(-1);
        try {
            new ECFieldFp(negative);
            throw new RuntimeException("Expected IllegalArgumentException for negative prime");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNullPrime() {
        try {
            new ECFieldFp(null);
            throw new RuntimeException("Expected NullPointerException for null prime");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
