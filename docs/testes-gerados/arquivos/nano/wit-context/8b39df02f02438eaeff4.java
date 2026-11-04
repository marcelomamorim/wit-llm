/*
 * @test
 * @summary Test for ECFieldFp constructor with positive and non-positive BigInteger
 * @run main ECFieldFpWitupTest
 */

import java.math.BigInteger;
import java.security.spec.ECFieldFp;

public class ECFieldFpWitupTest {

    public static void main(String[] args) {
        testValidP();
        testZeroP();
        testNegativeP();
    }

    private static void testValidP() {
        BigInteger p = BigInteger.valueOf(17);
        ECFieldFp field = new ECFieldFp(p);
        assert field.getP().equals(p) : "Expected p to be " + p;
        assert field.getFieldSize() == p.bitLength() : "Field size mismatch";
    }

    private static void testZeroP() {
        BigInteger p = BigInteger.ZERO;
        try {
            new ECFieldFp(p);
            assert false : "Expected IllegalArgumentException for zero p";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("p is not positive") : "Unexpected message: " + e.getMessage();
        }
    }

    private static void testNegativeP() {
        BigInteger p = BigInteger.valueOf(-5);
        try {
            new ECFieldFp(p);
            assert false : "Expected IllegalArgumentException for negative p";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("p is not positive") : "Unexpected message: " + e.getMessage();
        }
    }
}
