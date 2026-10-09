/* @test
   @summary Verifies decrementExact method throws ArithmeticException on Long.MIN_VALUE and returns correct value otherwise.
   @run main MathWitupTest_P60b853b050c5
 */
import java.lang.Math;
public class MathWitupTest_P60b853b050c5 {
    public static void main(String[] args) {
        testDecrementExactNormal();
        testDecrementExactOverflow();
    }
    private static void testDecrementExactNormal() {
        long input = 100L;
        long expected = 99L;
        long result = Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }
    private static void testDecrementExactOverflow() {
        long input = Long.MIN_VALUE;
        try {
            Math.decrementExact(input);
            throw new AssertionError("Expected ArithmeticException for Long.MIN_VALUE");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
