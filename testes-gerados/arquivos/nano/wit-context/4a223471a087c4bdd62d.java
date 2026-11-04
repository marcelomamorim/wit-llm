/* @test    @summary Test for BigInteger.longValueExact() method to verify correct behavior and exception handling.    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testLongValueExactWithinRange();
        testLongValueExactOutOfRange();
    }

    private static void testLongValueExactWithinRange() {
        // Create a BigInteger within the range of long
        BigInteger bi = BigInteger.valueOf(123456789L);
        long result = bi.longValueExact();
        if (result != 123456789L) {
            throw new AssertionError("Expected 123456789L, but got " + result);
        }
    }

    private static void testLongValueExactOutOfRange() {
        // Create a BigInteger outside the range of long to trigger exception
        // Using a value larger than Long.MAX_VALUE
        BigInteger bi = new BigInteger("9223372036854775808"); // Long.MAX_VALUE + 1
        boolean exceptionThrown = false;
        try {
            bi.longValueExact();
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException was not thrown for out-of-range value.");
        }
    }
}
