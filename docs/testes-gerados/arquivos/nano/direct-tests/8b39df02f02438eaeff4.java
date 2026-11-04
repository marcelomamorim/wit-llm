/* @test    @summary Test ECFieldFp constructor with valid and invalid BigInteger p    @run main ECFieldFpWitupTest */

import java.security.spec.ECFieldFp;
import java.math.BigInteger;

public class ECFieldFpWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testZeroBigInteger();
        testNegativeBigInteger();
    }

    private static void testValidConstructor() {
        BigInteger p = BigInteger.valueOf(17);
        ECFieldFp field = new ECFieldFp(p);
        assert field.getP().equals(p) : "Expected p to be equal to input";
        assert field.getFieldSize() == p.bitLength() : "Field size should match bit length of p";
    }

    private static void testZeroBigInteger() {
        BigInteger zero = BigInteger.ZERO;
        try {
            new ECFieldFp(zero);
            assert false : "Expected IllegalArgumentException for zero p";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("p is not positive") : "Unexpected exception message";
        }
    }

    private static void testNegativeBigInteger() {
        BigInteger negative = BigInteger.valueOf(-5);
        try {
            new ECFieldFp(negative);
            assert false : "Expected IllegalArgumentException for negative p";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("p is not positive") : "Unexpected exception message";
        }
    }
}
