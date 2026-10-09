/* @test
   @summary Testa o método subtractExact para detectar overflow de long.
   @run main MathWitupTest_P71be9ba3d3b8
 */
import java.lang.Math;
public class MathWitupTest_P71be9ba3d3b8 {
    public static void main(String[] args) {
        testSubtractExactNormal();
        testSubtractExactOverflow();
    }
    
    static void testSubtractExactNormal() {
        long x = 100L;
        long y = 50L;
        long expected = 50L;
        long result = Math.subtractExact(x, y);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }
    
    static void testSubtractExactOverflow() {
        long x = Long.MAX_VALUE;
        long y = -1L;
        try {
            Math.subtractExact(x, y);
            throw new AssertionError("Expected ArithmeticException for overflow");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
