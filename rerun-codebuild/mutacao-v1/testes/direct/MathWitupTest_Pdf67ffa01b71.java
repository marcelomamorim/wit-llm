/* @test    @summary Test for Math.addExact method overflow detection    @run main MathWitupTest_Pdf67ffa01b71 */

public class MathWitupTest_Pdf67ffa01b71 {

    public static void main(String[] args) {
        testAddExactNoOverflow();
        testAddExactPositiveOverflow();
        testAddExactNegativeOverflow();
    }

    static void testAddExactNoOverflow() {
        long x = 100L;
        long y = 200L;
        long result = Math.addExact(x, y);
        if (result != x + y) {
            throw new AssertionError("Failed: addExact with no overflow");
        }
    }

    static void testAddExactPositiveOverflow() {
        long x = Long.MAX_VALUE;
        long y = 1L;
        try {
            Math.addExact(x, y);
            throw new AssertionError("Failed: addExact did not throw on positive overflow");
        } catch (ArithmeticException e) {
            // Expected
        }
    }

    static void testAddExactNegativeOverflow() {
        long x = Long.MIN_VALUE;
        long y = -1L;
        try {
            Math.addExact(x, y);
            throw new AssertionError("Failed: addExact did not throw on negative overflow");
        } catch (ArithmeticException e) {
            // Expected
        }
    }
}
