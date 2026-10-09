/* @test
   @summary Testa o método decrementExact de java.lang.Math.
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
            throw new AssertionError("Failed: decrementExact(" + input + ") = " + result);
        }
    }
    private static void testDecrementExactOverflow() {
        long input = Long.MIN_VALUE;
        boolean exceptionThrown = false;
        try {
            Math.decrementExact(input);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException for input: " + input);
        }
    }
}
