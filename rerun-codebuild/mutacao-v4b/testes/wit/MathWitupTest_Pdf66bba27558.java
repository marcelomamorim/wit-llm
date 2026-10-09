/* @test
   @summary Testa o método addExact de java.lang.Math para detectar overflow de long.
   @run main MathWitupTest_Pdf66bba27558
 */
import java.lang.Math;
public class MathWitupTest_Pdf66bba27558 {
    public static void main(String[] args) {
        testAddExactNormal();
        testAddExactOverflow();
    }
    static void testAddExactNormal() {
        long x = 1000L;
        long y = 2000L;
        long result = Math.addExact(x, y);
        if (result != (x + y)) {
            throw new AssertionError("addExact normal case failed");
        }
    }
    static void testAddExactOverflow() {
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
