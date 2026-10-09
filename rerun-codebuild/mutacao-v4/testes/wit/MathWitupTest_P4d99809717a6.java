/* @test
   @summary Testa o método Math.incrementExact para comportamento normal e de overflow.
   @run main MathWitupTest_P4d99809717a6
 */
import java.lang.Math;
public class MathWitupTest_P4d99809717a6 {
    public static void main(String[] args) {
        testIncrementExactNormal();
        testIncrementExactOverflow();
    }
    private static void testIncrementExactNormal() {
        long input = 42L;
        long expected = 43L;
        long result = Math.incrementExact(input);
        if (result != expected) {
            throw new AssertionError("Failed normal case: expected " + expected + " but got " + result);
        }
    }
    private static void testIncrementExactOverflow() {
        long input = Long.MAX_VALUE;
        try {
            Math.incrementExact(input);
            throw new AssertionError("Expected ArithmeticException for overflow not thrown");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
