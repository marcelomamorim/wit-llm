/* @test
   @summary Testa o método pow de BigInteger para diferentes cenários.
   @run main BigIntegerWitupTest_P4d1ba69ed4bc
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_P4d1ba69ed4bc {
    public static void main(String[] args) {
        testPositiveExponent();
        testZeroExponent();
        testNegativeExponent();
        testZeroBase();
        testOneBase();
        testLargeExponent();
        testOverflowExponent();
    }

    static void testPositiveExponent() {
        BigInteger base = BigInteger.valueOf(2);
        int exponent = 10;
        BigInteger result = base.pow(exponent);
        BigInteger expected = BigInteger.valueOf(1024);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: 2^10 != 1024, got " + result);
        }
    }

    static void testZeroExponent() {
        BigInteger base = BigInteger.valueOf(12345);
        int exponent = 0;
        BigInteger result = base.pow(exponent);
        BigInteger expected = BigInteger.ONE;
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: any^0 != 1, got " + result);
        }
    }

    static void testNegativeExponent() {
        BigInteger base = BigInteger.valueOf(5);
        int exponent = -3;
        try {
            base.pow(exponent);
            throw new AssertionError("Expected ArithmeticException for negative exponent");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testZeroBase() {
        BigInteger base = BigInteger.ZERO;
        int exponent = 5;
        BigInteger result = base.pow(exponent);
        BigInteger expected = BigInteger.ZERO;
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: 0^5 != 0, got " + result);
        }
    }

    static void testOneBase() {
        BigInteger base = BigInteger.ONE;
        int exponent = 123;
        BigInteger result = base.pow(exponent);
        BigInteger expected = BigInteger.ONE;
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: 1^123 != 1, got " + result);
        }
    }

    static void testLargeExponent() {
        BigInteger base = BigInteger.valueOf(3);
        int exponent = 20;
        BigInteger result = base.pow(exponent);
        BigInteger expected = new BigInteger("3486784401");
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: 3^20 != 3486784401, got " + result);
        }
    }

    static void testOverflowExponent() {
        BigInteger base = BigInteger.valueOf(2);
        int exponent = Integer.MAX_VALUE / 2 + 1;
        try {
            base.pow(exponent);
            // No exception expected here, result may be large
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for large exponent", e);
        }
    }
}
