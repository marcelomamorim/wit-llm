/* @test    @summary Testa o método BigInteger.sqrt() para entradas não negativas.    @run main BigIntegerWitupTest_Pded278848356 */

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
            throw new AssertionError("Failed: sqrt(16) should be 4, got " + result);
        }
    }

    static void testSqrtZero() {
        BigInteger bi = BigInteger.ZERO;
        BigInteger result = bi.sqrt();
        if (!result.equals(BigInteger.ZERO)) {
            throw new AssertionError("Failed: sqrt(0) should be 0, got " + result);
        }
    }

    static void testSqrtNegative() {
        BigInteger bi = BigInteger.valueOf(-1);
        try {
            bi.sqrt();
            throw new AssertionError("Expected ArithmeticException for negative input");
        } catch (ArithmeticException e) {
            if (!"Negative BigInteger".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
