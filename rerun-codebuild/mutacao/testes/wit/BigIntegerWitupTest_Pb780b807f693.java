/* @test    @summary Test for BigInteger.mod(BigInteger m) method, focusing on exception path when modulus is non-positive.    @run main BigIntegerWitupTest_Pb780b807f693 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pb780b807f693 {

    public static void main(String[] args) {
        testModWithNonPositiveModulus();
        testModWithPositiveModulus();
    }

    private static void testModWithNonPositiveModulus() {
        BigInteger value = BigInteger.valueOf(10);
        BigInteger zero = BigInteger.ZERO;
        BigInteger negative = BigInteger.valueOf(-5);
        try {
            value.mod(zero);
            System.err.println("Failed: Expected ArithmeticException for modulus zero");
        } catch (ArithmeticException e) {
            System.out.println("Passed: Caught expected ArithmeticException for modulus zero");
        }
        try {
            value.mod(negative);
            System.err.println("Failed: Expected ArithmeticException for negative modulus");
        } catch (ArithmeticException e) {
            System.out.println("Passed: Caught expected ArithmeticException for negative modulus");
        }
    }

    private static void testModWithPositiveModulus() {
        BigInteger value = BigInteger.valueOf(10);
        BigInteger positive = BigInteger.valueOf(3);
        BigInteger result = value.mod(positive);
        if (result.equals(BigInteger.valueOf(1))) {
            System.out.println("Passed: 10 mod 3 == 1");
        } else {
            System.err.println("Failed: 10 mod 3 != 1, got " + result);
        }
        BigInteger negativeValue = BigInteger.valueOf(-10);
        BigInteger result2 = negativeValue.mod(positive);
        if (result2.equals(BigInteger.valueOf(2))) {
            System.out.println("Passed: -10 mod 3 == 2");
        } else {
            System.err.println("Failed: -10 mod 3 != 2, got " + result2);
        }
    }
}
