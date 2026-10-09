/* @test    @summary Tests for BigInteger.intValueExact() behavior    @run main BigIntegerWitupTest_Pca00e9918761 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pca00e9918761 {
    public static void main(String[] args) {
        testWithinIntRange();
        testOutsideIntRange();
        testNegativeValueWithinRange();
        testNegativeValueOutsideRange();
    }

    static void testWithinIntRange() {
        BigInteger bi = BigInteger.valueOf(12345L);
        int result = bi.intValueExact();
        assert result == 12345 : "Expected 12345, but got " + result;
    }

    static void testOutsideIntRange() {
        byte[] largeBytes = { (byte)0x80, 0x00, 0x00, 0x00, 0x00 };
        BigInteger bi = new BigInteger(largeBytes);
        boolean threw = false;
        try {
            bi.intValueExact();
        } catch (ArithmeticException e) {
            threw = true;
        }
        assert threw : "Expected ArithmeticException for value outside int range";
    }

    static void testNegativeValueWithinRange() {
        BigInteger bi = BigInteger.valueOf(-12345L);
        int result = bi.intValueExact();
        assert result == -12345 : "Expected -12345, but got " + result;
    }

    static void testNegativeValueOutsideRange() {
        byte[] largeBytes = { (byte)0xFF, (byte)0x80, 0x00, 0x00, 0x00 };
        BigInteger bi = new BigInteger(largeBytes);
        boolean threw = false;
        try {
            bi.intValueExact();
        } catch (ArithmeticException e) {
            threw = true;
        }
        assert threw : "Expected ArithmeticException for negative value outside int range";
    }
}
