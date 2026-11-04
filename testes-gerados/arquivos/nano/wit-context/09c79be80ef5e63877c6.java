/* @test    @summary Test BigDecimal constructor with int and MathContext for exception path    @run main BigDecimalWitupTest */

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalWitupTest {

    public static void main(String[] args) {
        testConstructorWithMathContextThrows();
    }

    private static void testConstructorWithMathContextThrows() {
        MathContext mc = new MathContext(1);
        int val = 12345;
        try {
            // Simulate a MathContext with precision > 0 to trigger potential exception
            new BigDecimal(val, mc);
        } catch (ArithmeticException e) {
            System.out.println("Caught expected ArithmeticException");
            return;
        }
        System.out.println("No exception thrown");
    }
}
