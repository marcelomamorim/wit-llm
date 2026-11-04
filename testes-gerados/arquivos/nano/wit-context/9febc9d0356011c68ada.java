/* @test    @summary Test for BigInteger.intValueExact() method to verify exception throwing on out-of-range values    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testIntValueExactThrows();
        testIntValueExactWithinRange();
    }

    private static void testIntValueExactThrows() {
        // Create a BigInteger with magnitude larger than Integer.MAX_VALUE
        byte[] largeMag = { (byte)0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
        BigInteger bigInt = new BigInteger(largeMag);
        try {
            bigInt.intValueExact();
            throw new AssertionError("Expected ArithmeticException was not thrown for out-of-range BigInteger.");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }

    private static void testIntValueExactWithinRange() {
        // Create a BigInteger within int range
        BigInteger withinRange = BigInteger.valueOf(12345);
        int result = withinRange.intValueExact();
        if (result != 12345) {
            throw new AssertionError("Expected 12345, but got " + result);
        }
    }
}
