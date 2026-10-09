/* @test    @summary Test for BigInteger.longValueExact() method behavior.    @run main BigIntegerWitupTest_Pabefedfc00b6 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pabefedfc00b6 {

    public static void main(String[] args) {
        testWithinLongRange();
        testOutOfLongRange();
    }

    static void testWithinLongRange() {
        // Create BigInteger with value within long range
        BigInteger bi = BigInteger.valueOf(Long.MAX_VALUE);
        long result = bi.longValueExact();
        if (result != Long.MAX_VALUE) {
            throw new AssertionError("Expected Long.MAX_VALUE, but got " + result);
        }
        // Test with a small positive value
        BigInteger bi2 = BigInteger.valueOf(12345L);
        long result2 = bi2.longValueExact();
        if (result2 != 12345L) {
            throw new AssertionError("Expected 12345, but got " + result2);
        }
        // Test with a small negative value
        BigInteger bi3 = BigInteger.valueOf(-98765L);
        long result3 = bi3.longValueExact();
        if (result3 != -98765L) {
            throw new AssertionError("Expected -98765, but got " + result3);
        }
    }

    static void testOutOfLongRange() {
        // Create BigInteger with value exceeding Long.MAX_VALUE
        BigInteger bi = new BigInteger("9223372036854775808"); // Long.MAX_VALUE + 1
        try {
            bi.longValueExact();
            throw new AssertionError("Expected ArithmeticException for value exceeding long range");
        } catch (ArithmeticException e) {
            // Expected exception
        }
        // Create BigInteger with value less than Long.MIN_VALUE
        BigInteger bi2 = new BigInteger("-9223372036854775809"); // Long.MIN_VALUE - 1
        try {
            bi2.longValueExact();
            throw new AssertionError("Expected ArithmeticException for value below long range");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
