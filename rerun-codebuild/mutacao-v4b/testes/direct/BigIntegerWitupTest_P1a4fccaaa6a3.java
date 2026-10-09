/* @test
   @summary Tests byteValueExact() method of BigInteger.
   @run main BigIntegerWitupTest_P1a4fccaaa6a3
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_P1a4fccaaa6a3 {
    public static void main(String[] args) {
        testWithinRange();
        testOutOfRange();
    }
    static void testWithinRange() {
        BigInteger bi = BigInteger.valueOf(127);
        byte result = bi.byteValueExact();
        if (result != 127) {
            throw new AssertionError("Expected 127, got " + result);
        }
        bi = BigInteger.valueOf(-128);
        result = bi.byteValueExact();
        if (result != -128) {
            throw new AssertionError("Expected -128, got " + result);
        }
    }
    static void testOutOfRange() {
        BigInteger bi = BigInteger.valueOf(128);
        try {
            bi.byteValueExact();
            throw new AssertionError("Expected ArithmeticException for value 128");
        } catch (ArithmeticException e) {
            // expected
        }
        bi = BigInteger.valueOf(-129);
        try {
            bi.byteValueExact();
            throw new AssertionError("Expected ArithmeticException for value -129");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
