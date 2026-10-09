/* @test    @summary Test BigInteger.sqrt() method for non-negative and negative inputs    @run main BigIntegerWitupTest_Pded278848356 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pded278848356 {

    public static void main(String[] args) {
        testSqrtPositive();
        testSqrtZero();
        testSqrtNegative();
    }

    static void testSqrtPositive() {
        BigInteger bi = BigInteger.valueOf(16);
        BigInteger result = bi.sqrt();
        if (!result.equals(BigInteger.valueOf(4))) {
            throw new AssertionError("Failed: sqrt(16) != 4, got " + result);
        }
    }

    static void testSqrtZero() {
        BigInteger bi = BigInteger.ZERO;
        BigInteger result = bi.sqrt();
        if (!result.equals(BigInteger.ZERO)) {
            throw new AssertionError("Failed: sqrt(0) != 0, got " + result);
        }
    }

    static void testSqrtNegative() {
        BigInteger bi = BigInteger.valueOf(-1);
        try {
            bi.sqrt();
            throw new AssertionError("Expected ArithmeticException for negative input");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
