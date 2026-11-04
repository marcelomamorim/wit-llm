/* @test    @summary Teste do método BigInteger.sqrt() para verificar comportamento esperado e exceções.    @run main BigIntegerWitupTest */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        testPositiveNumber();
        testZero();
        testNegativeNumber();
    }

    private static void testPositiveNumber() {
        BigInteger bi = BigInteger.valueOf(16);
        BigInteger result = bi.sqrt();
        if (!result.equals(BigInteger.valueOf(4))) {
            throw new AssertionError("Expected sqrt of 16 to be 4, but got: " + result);
        }
    }

    private static void testZero() {
        BigInteger bi = BigInteger.ZERO;
        BigInteger result = bi.sqrt();
        if (!result.equals(BigInteger.ZERO)) {
            throw new AssertionError("Expected sqrt of 0 to be 0, but got: " + result);
        }
    }

    private static void testNegativeNumber() {
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
