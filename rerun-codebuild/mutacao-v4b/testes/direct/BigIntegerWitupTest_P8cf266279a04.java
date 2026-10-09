/* @test
   @summary Tests the sqrt() method of BigInteger for non-negative and negative inputs.
   @run main BigIntegerWitupTest_P8cf266279a04
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_P8cf266279a04 {
    public static void main(String[] args) {
        testSqrtNonNegative();
        testSqrtNegative();
    }

    private static void testSqrtNonNegative() {
        BigInteger zero = BigInteger.ZERO;
        BigInteger one = BigInteger.ONE;
        BigInteger four = BigInteger.valueOf(4);
        BigInteger nine = BigInteger.valueOf(9);
        BigInteger sixteen = BigInteger.valueOf(16);

        assert zero.sqrt().equals(zero) : "sqrt(0) should be 0";
        assert one.sqrt().equals(one) : "sqrt(1) should be 1";
        assert four.sqrt().equals(BigInteger.valueOf(2)) : "sqrt(4) should be 2";
        assert nine.sqrt().equals(BigInteger.valueOf(3)) : "sqrt(9) should be 3";
        assert sixteen.sqrt().equals(BigInteger.valueOf(4)) : "sqrt(16) should be 4";
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
}
