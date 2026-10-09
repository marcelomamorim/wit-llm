/* @test    @summary Test BigInteger.flipBit for valid and invalid inputs    @run main BigIntegerWitupTest_Pcf9ad0b9c1d4 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pcf9ad0b9c1d4 {

    public static void main(String[] args) {
        testFlipBitValid();
        testFlipBitNegative();
    }

    private static void testFlipBitValid() {
        try {
            BigInteger bi = BigInteger.valueOf(42); // arbitrary value
            int n = 0;
            BigInteger result = bi.flipBit(n);
            if (result == null) {
                throw new AssertionError("flipBit returned null for valid input");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input: " + e);
        }
    }

    private static void testFlipBitNegative() {
        try {
            BigInteger bi = BigInteger.valueOf(42); // arbitrary value
            int n = -1;
            BigInteger result = bi.flipBit(n);
            throw new AssertionError("Expected ArithmeticException for negative n, but got: " + result);
        } catch (ArithmeticException e) {
            // Expected exception
            if (!e.getMessage().contains("Negative")) {
                throw new AssertionError("ArithmeticException message mismatch: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
