/*
 * @test
 * @summary Tests for BigInteger.byteValueExact()
 * @run main BigIntegerWitupTest_Pc8ff69e43552
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_Pc8ff69e43552 {
    public static void main(String[] args) {
        testByteValueExactWithinRange();
        testByteValueExactOutOfRange();
    }
    static void testByteValueExactWithinRange() {
        // Create BigInteger within byte range
        BigInteger bi = BigInteger.valueOf(127); // Byte.MAX_VALUE
        byte result = bi.byteValueExact();
        assert result == 127 : "Expected 127, got " + result;
        bi = BigInteger.valueOf(-128); // Byte.MIN_VALUE
        result = bi.byteValueExact();
        assert result == -128 : "Expected -128, got " + result;
    }
    static void testByteValueExactOutOfRange() {
        // Create BigInteger outside byte range, expect ArithmeticException
        BigInteger bi = BigInteger.valueOf(128); // one above Byte.MAX_VALUE
        try {
            bi.byteValueExact();
            assert false : "Expected ArithmeticException for value 128";
        } catch (ArithmeticException e) {
            // Expected
        }
        bi = BigInteger.valueOf(-129); // one below Byte.MIN_VALUE
        try {
            bi.byteValueExact();
            assert false : "Expected ArithmeticException for value -129";
        } catch (ArithmeticException e) {
            // Expected
        }
    }
}
