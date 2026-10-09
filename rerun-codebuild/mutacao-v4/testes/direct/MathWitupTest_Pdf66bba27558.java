/* @test
   @summary Verifies the addExact method for normal and overflow cases.
   @run main MathWitupTest_Pdf66bba27558
 */
import java.lang.Math;
public class MathWitupTest_Pdf66bba27558 {
    public static void main(String[] args) {
        testAddExactNormal();
        testAddExactOverflow();
    }
    private static void testAddExactNormal() {
        long x = 100L;
        long y = 200L;
        long result = Math.addExact(x, y);
        if (result != 300L) {
            throw new AssertionError("Expected 300, got " + result);
        }
    }
    private static void testAddExactOverflow() {
        long x = Long.MAX_VALUE;
        long y = 1L;
        boolean exceptionThrown = false;
        try {
            Math.addExact(x, y);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException for overflow");
        }
    }
}
