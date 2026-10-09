/* @test
   @summary Tests BigInteger.pow(int) for positive, zero, and negative exponents.
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
        testNegativeBaseOddExponent();
        testNegativeBaseEvenExponent();
    }

    static void testPositiveExponent() {
        BigInteger base = BigInteger.valueOf(2);
        int exponent = 10;
        BigInteger result = base.pow(exponent);
        BigInteger expected = BigInteger.valueOf(1024);
        if (!result.equals(expected)) {
            throw new AssertionError("Positive exponent test failed: " + result + " != " + expected);
        }
    }

    static void testZeroExponent() {
        BigInteger base = BigInteger.valueOf(12345);
        int exponent = 0;
        BigInteger result = base.pow(exponent);
        BigInteger expected = BigInteger.ONE;
        if (!result.equals(expected)) {
            throw new AssertionError("Zero exponent test failed: " + result + " != " + expected);
        }
    }

    static void testNegativeExponent() {
        BigInteger base = BigInteger.valueOf(2);
        int exponent = -3;
        try {
            base.pow(exponent);
            throw new AssertionError("Negative exponent did not throw exception");
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
            throw new AssertionError("Zero base test failed: " + result + " != " + expected);
        }
        // 0^0 is defined as 1 in BigInteger
        BigInteger zeroExponentResult = base.pow(0);
        if (!zeroExponentResult.equals(BigInteger.ONE)) {
            throw new AssertionError("0^0 test failed: " + zeroExponentResult);
        }
    }

    static void testOneBase() {
        BigInteger base = BigInteger.ONE;
        int exponent = 123;
        BigInteger result = base.pow(exponent);
        if (!result.equals(BigInteger.ONE)) {
            throw new AssertionError("1 base test failed: " + result);
        }
    }

    static void testNegativeBaseOddExponent() {
        BigInteger base = BigInteger.valueOf(-3);
        int exponent = 3;
        BigInteger result = base.pow(exponent);
        BigInteger expected = BigInteger.valueOf(-27);
        if (!result.equals(expected)) {
            throw new AssertionError("Negative base odd exponent failed: " + result + " != " + expected);
        }
    }

    static void testNegativeBaseEvenExponent() {
        BigInteger base = BigInteger.valueOf(-3);
        int exponent = 4;
        BigInteger result = base.pow(exponent);
        BigInteger expected = BigInteger.valueOf(81);
        if (!result.equals(expected)) {
            throw new AssertionError("Negative base even exponent failed: " + result + " != " + expected);
        }
    }
}
