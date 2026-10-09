/* @test
   @summary Testa o método Math.negateExact para verificar comportamento esperado.
   @run main MathWitupTest_Pf9abe15cbf43
 */
import java.lang.Math;
public class MathWitupTest_Pf9abe15cbf43 {
    public static void main(String[] args) {
        testNegateExactNormal();
        testNegateExactOverflow();
    }
    
    static void testNegateExactNormal() {
        long input = 123456789L;
        long expected = -123456789L;
        long result = Math.negateExact(input);
        if (result != expected) {
            throw new AssertionError("Failed: negateExact(" + input + ") = " + result + ", expected " + expected);
        }
    }
    
    static void testNegateExactOverflow() {
        long input = Long.MIN_VALUE;
        boolean exceptionThrown = false;
        try {
            Math.negateExact(input);
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException for input " + input);
        }
    }
}
