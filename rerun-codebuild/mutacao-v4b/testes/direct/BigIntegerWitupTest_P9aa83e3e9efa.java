/* @test
   @summary Tests the intValueExact() method of BigInteger for values within and outside int range.
   @run main BigIntegerWitupTest_P9aa83e3e9efa
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_P9aa83e3e9efa {
    public static void main(String[] args) {
        testWithinRange();
        testOutOfRange();
    }
    private static void testWithinRange() {
        BigInteger bi = BigInteger.valueOf(Integer.MAX_VALUE);
        int result = bi.intValueExact();
        if (result != Integer.MAX_VALUE) {
            throw new AssertionError("Expected " + Integer.MAX_VALUE + " but got " + result);
        }
        bi = BigInteger.valueOf(Integer.MIN_VALUE);
        result = bi.intValueExact();
        if (result != Integer.MIN_VALUE) {
            throw new AssertionError("Expected " + Integer.MIN_VALUE + " but got " + result);
        }
    }
    private static void testOutOfRange() {
        BigInteger bi = BigInteger.valueOf(Long.MAX_VALUE);
        try {
            bi.intValueExact();
            throw new AssertionError("Expected ArithmeticException for value out of int range");
        } catch (ArithmeticException e) {
            // expected
        }
        bi = BigInteger.valueOf(Long.MIN_VALUE);
        try {
            bi.intValueExact();
            throw new AssertionError("Expected ArithmeticException for value out of int range");
        } catch (ArithmeticException e) {
            // expected
        }
        bi = new BigInteger(new byte[] {0, 0, 0, 0, 0, 0, 0, 1}); // 1 in big-endian
        int val = bi.intValueExact();
        if (val != 1) {
            throw new AssertionError("Expected 1 but got " + val);
        }
        bi = new BigInteger(new byte[] {(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, 0, 0, 0, 0}); // negative number
        try {
            bi.intValueExact();
            throw new AssertionError("Expected ArithmeticException for negative out of int range");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
