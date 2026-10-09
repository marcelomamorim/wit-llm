/* @test    @summary Test byteValueExact() method of BigInteger for values within byte range and outside it    @run main BigIntegerWitupTest_Pc5db96e8498e */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pc5db96e8498e {

    public static void main(String[] args) {
        testByteValueExactWithinRange();
        testByteValueExactOutOfRange();
    }

    private static void testByteValueExactWithinRange() {
        // Create BigInteger with value within byte range (-128 to 127)
        BigInteger bi = BigInteger.valueOf(100);
        byte result = bi.byteValueExact();
        assert result == 100 : "Expected 100, but got " + result;
    }

    private static void testByteValueExactOutOfRange() {
        // Create BigInteger with value outside byte range
        BigInteger bi = BigInteger.valueOf(200);
        boolean exceptionThrown = false;
        try {
            bi.byteValueExact();
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected ArithmeticException for value outside byte range";
    }
}
