/* @test
   @summary Tests the sqrt() method of BigInteger.
   @run main BigIntegerWitupTest_P8cf266279a04
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_P8cf266279a04 {
    public static void main(String[] args) {
        testSqrtNegative();
        testSqrtZero();
        testSqrtPositive();
    }
    private static void testSqrtNegative() {
        BigInteger negative = BigInteger.valueOf(-1);
        try {
            negative.sqrt();
            throw new AssertionError("Expected ArithmeticException for negative input");
        } catch (ArithmeticException e) {
            // expected
        }
    }
    private static void testSqrtZero() {
        BigInteger zero = BigInteger.ZERO;
        BigInteger result = zero.sqrt();
        if (!result.equals(BigInteger.ZERO)) {
            throw new AssertionError("sqrt(0) should be 0");
        }
    }
    private static void testSqrtPositive() {
        BigInteger num = new BigInteger("16");
        BigInteger expected = new BigInteger("4");
        BigInteger result = num.sqrt();
        if (!result.equals(expected)) {
            throw new AssertionError("sqrt(16) should be 4");
        }
        BigInteger num2 = new BigInteger("15");
        BigInteger expected2 = new BigInteger("3");
        BigInteger result2 = num2.sqrt();
        if (!result2.equals(expected2)) {
            throw new AssertionError("sqrt(15) should be 3");
        }
    }
}
