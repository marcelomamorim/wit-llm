/* @test
   @summary Tests the negateExact method of java.lang.Math for correct behavior.
   @run main MathWitupTest_Pf9abe15cbf43
 */
import java.lang.Math;
public class MathWitupTest_Pf9abe15cbf43 {
    public static void main(String[] args) {
        testNegateExactNormal();
        testNegateExactOverflow();
    }
    private static void testNegateExactNormal() {
        long value = 123456789L;
        long result = Math.negateExact(value);
        if (result != -value) {
            throw new AssertionError("Expected -" + value + " but got " + result);
        }
    }
    private static void testNegateExactOverflow() {
        long minValue = Long.MIN_VALUE;
        try {
            Math.negateExact(minValue);
            throw new AssertionError("Expected ArithmeticException for Long.MIN_VALUE");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
