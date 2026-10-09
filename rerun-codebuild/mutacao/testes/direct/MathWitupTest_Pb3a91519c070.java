/* @test    @summary Test for Math.incrementExact method to verify correct increment and overflow exception handling    @run main MathWitupTest_Pb3a91519c070 */

import java.lang.Math;

public class MathWitupTest_Pb3a91519c070 {

    public static void main(String[] args) {
        testIncrementExactNormalCase();
        testIncrementExactOverflow();
    }

    private static void testIncrementExactNormalCase() {
        long input = 42L;
        long expected = 43L;
        long result = Math.incrementExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testIncrementExactOverflow() {
        long input = Long.MAX_VALUE;
        boolean exceptionThrown = false;
        try {
            Math.incrementExact(input);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException for overflow not thrown");
        }
    }
}
