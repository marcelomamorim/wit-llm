/* @test    @summary Test the BigInteger.pow(int) method for exception paths and behavior    @run main BigIntegerWitupTest_P36d2aa1a7711 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P36d2aa1a7711 {

    public static void main(String[] args) {
        testNegativeExponent();
        testZeroExponent();
        testPositiveExponent();
        testLargeExponentOverflow();
        testZeroBigIntegerPower();
    }

    static void testNegativeExponent() {
        BigInteger bi = BigInteger.valueOf(2);
        try {
            bi.pow(-1);
            System.err.println("Failed: Expected ArithmeticException for negative exponent");
        } catch (ArithmeticException e) {
            System.out.println("Passed: Negative exponent throws ArithmeticException");
        }
    }

    static void testZeroExponent() {
        BigInteger bi = BigInteger.valueOf(12345);
        BigInteger result = bi.pow(0);
        if (result.equals(BigInteger.ONE)) {
            System.out.println("Passed: Any number to the power 0 is 1");
        } else {
            System.err.println("Failed: Power 0 did not return 1");
        }
    }

    static void testPositiveExponent() {
        BigInteger bi = BigInteger.valueOf(3);
        BigInteger result = bi.pow(4);
        BigInteger expected = BigInteger.valueOf(81); // 3^4
        if (result.equals(expected)) {
            System.out.println("Passed: 3^4 equals 81");
        } else {
            System.err.println("Failed: 3^4 did not equal 81");
        }
    }

    static void testLargeExponentOverflow() {
        BigInteger bi = BigInteger.valueOf(2);
        try {
            // Exponent large enough to cause overflow in bitsToShift calculation
            bi.pow(100000);
            System.out.println("Note: Large exponent did not throw overflow report, check behavior");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for large exponent: " + e);
        }
    }

    static void testZeroBigIntegerPower() {
        BigInteger zero = BigInteger.ZERO;
        BigInteger result = zero.pow(5);
        if (result.equals(BigInteger.ZERO)) {
            System.out.println("Passed: 0^n is 0 for n > 0");
        } else {
            System.err.println("Failed: 0^n did not return 0");
        }
    }
}
