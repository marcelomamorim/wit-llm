/* @test
   @summary Tests subtractExact method for overflow and normal subtraction.
   @run main MathWitupTest_P71be9ba3d3b8
 */
import java.lang.Math;
public class MathWitupTest_P71be9ba3d3b8 {
    public static void main(String[] args) {
        testSubtractExactNormal();
        testSubtractExactOverflow();
    }
    private static void testSubtractExactNormal() {
        long x = 100L;
        long y = 50L;
        long result = Math.subtractExact(x, y);
        if (result != 50L) {
            throw new AssertionError("Expected 50, got " + result);
        }
    }
    private static void testSubtractExactOverflow() {
        long max = Long.MAX_VALUE;
        long min = Long.MIN_VALUE;
        // Overflow case: max - (-1) should throw
        try {
            Math.subtractExact(max, -1L);
            throw new AssertionError("Expected ArithmeticException for overflow");
        } catch (ArithmeticException e) {
            // expected
        }
        // Overflow case: min - 1 should throw
        try {
            Math.subtractExact(min, 1L);
            throw new AssertionError("Expected ArithmeticException for overflow");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
