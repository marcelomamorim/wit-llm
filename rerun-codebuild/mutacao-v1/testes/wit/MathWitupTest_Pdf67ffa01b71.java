/* @test    @summary Test for Math.addExact to verify overflow exception behavior    @run main MathWitupTest_Pdf67ffa01b71 */

import java.lang.Math;

public class MathWitupTest_Pdf67ffa01b71 {

    public static void main(String[] args) {
        testAddExactNoOverflow();
        testAddExactOverflow();
    }

    private static void testAddExactNoOverflow() {
        long x = 1000L;
        long y = 2000L;
        long result = Math.addExact(x, y);
        if (result != (x + y)) {
            throw new AssertionError("Failed: addExact with no overflow. Expected " + (x + y) + " but got " + result);
        }
    }

    private static void testAddExactOverflow() {
        long x = Long.MAX_VALUE;
        long y = 1L;
        try {
            Math.addExact(x, y);
            throw new AssertionError("Failed: addExact should throw ArithmeticException on overflow");
        } catch (ArithmeticException e) {
            if (!"long overflow".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
