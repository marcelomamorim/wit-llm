/*
 * @test
 * @summary Test constructor of ECFieldFp with valid and invalid BigInteger p
 * @run main ECFieldFpWitupTest
 */

import java.math.BigInteger;
import java.security.spec.ECFieldFp;

public class ECFieldFpWitupTest {
    public static void main(String[] args) {
        testValidPositiveP();
        testZeroPThrows();
        testNegativePThrows();
        testConstructorWithNullThrows();
    }

    private static void testValidPositiveP() {
        BigInteger p = BigInteger.valueOf(17);
        ECFieldFp field = new ECFieldFp(p);
        if (!field.getP().equals(p)) {
            throw new RuntimeException("Constructor did not set p correctly");
        }
        if (field.getFieldSize() != p.bitLength()) {
            throw new RuntimeException("getFieldSize() does not match bitLength of p");
        }
    }

    private static void testZeroPThrows() {
        try {
            new ECFieldFp(BigInteger.ZERO);
            throw new RuntimeException("Expected IllegalArgumentException for p=0");
        } catch (IllegalArgumentException e) {
            if (!"p is not positive".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message for p=0: " + e.getMessage());
            }
        }
    }

    private static void testNegativePThrows() {
        try {
            new ECFieldFp(BigInteger.valueOf(-1));
            throw new RuntimeException("Expected IllegalArgumentException for negative p");
        } catch (IllegalArgumentException e) {
            if (!"p is not positive".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message for negative p: " + e.getMessage());
            }
        }
    }

    private static void testConstructorWithNullThrows() {
        try {
            new ECFieldFp(null);
            throw new RuntimeException("Expected NullPointerException for null p");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
