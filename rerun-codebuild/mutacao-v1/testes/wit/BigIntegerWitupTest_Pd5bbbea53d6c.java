/* @test    @summary Test for BigInteger.mod(BigInteger m) handling of non-positive modulus    @run main BigIntegerWitupTest_Pd5bbbea53d6c */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pd5bbbea53d6c {

    public static void main(String[] args) {
        testModWithNonPositiveModulus();
    }

    private static void testModWithNonPositiveModulus() {
        BigInteger value = BigInteger.valueOf(12345L);
        BigInteger zero = BigInteger.ZERO;
        BigInteger negativeOne = BigInteger.valueOf(-1L);
        BigInteger negativeBig = BigInteger.valueOf(-98765L);

        // Test with modulus zero
        try {
            value.mod(zero);
            throw new AssertionError("Expected ArithmeticException for modulus zero");
        } catch (ArithmeticException e) {
            // Expected exception
        }

        // Test with negative modulus (-1)
        try {
            value.mod(negativeOne);
            throw new AssertionError("Expected ArithmeticException for negative modulus");
        } catch (ArithmeticException e) {
            // Expected exception
        }

        // Test with negative modulus (-98765)
        try {
            value.mod(negativeBig);
            throw new AssertionError("Expected ArithmeticException for negative modulus");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
