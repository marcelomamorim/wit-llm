/* @test
   @summary Tests the longValueExact() method of BigInteger for values within and outside long range.
   @run main BigIntegerWitupTest_Pdd60f5b31f3b
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_Pdd60f5b31f3b {
    public static void main(String[] args) {
        testWithinLongRange();
        testOutsideLongRange();
    }
    private static void testWithinLongRange() {
        BigInteger bi = BigInteger.valueOf(Long.MAX_VALUE);
        long result = bi.longValueExact();
        if (result != Long.MAX_VALUE) {
            throw new AssertionError("Expected Long.MAX_VALUE, got " + result);
        }
        bi = BigInteger.valueOf(Long.MIN_VALUE);
        result = bi.longValueExact();
        if (result != Long.MIN_VALUE) {
            throw new AssertionError("Expected Long.MIN_VALUE, got " + result);
        }
    }
    private static void testOutsideLongRange() {
        BigInteger biHigh = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE);
        try {
            biHigh.longValueExact();
            throw new AssertionError("Expected ArithmeticException for value > Long.MAX_VALUE");
        } catch (ArithmeticException e) {
            // expected
        }
        BigInteger biLow = BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.ONE);
        try {
            biLow.longValueExact();
            throw new AssertionError("Expected ArithmeticException for value < Long.MIN_VALUE");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
