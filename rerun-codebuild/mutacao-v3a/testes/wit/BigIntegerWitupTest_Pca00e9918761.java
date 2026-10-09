/* @test    @summary Test for BigInteger.intValueExact() exception path    @run main BigIntegerWitupTest_Pca00e9918761 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pca00e9918761 {

    public static void main(String[] args) {
        testIntValueExactThrows();
    }

    private static void testIntValueExactThrows() {
        // Caso onde mag.length > 1, deve lançar ArithmeticException
        byte[] mag = {1, 2};
        BigInteger bigInt = new BigInteger(1, mag);
        boolean exceptionThrown = false;
        try {
            bigInt.intValueExact();
        } catch (ArithmeticException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArithmeticException for mag.length > 1");
        }

        // Caso onde bitLength() > 31, mesmo mag.length == 1, deve lançar
        byte[] mag2 = {(byte)0x80}; // 128, bitLength() = 8, menor que 31, mas vamos criar um cenário 
        // para simular > 31 usando uma BigInteger maior (não possível com mag direto, então criamos um BigInteger grande)
        BigInteger bigLarge = BigInteger.valueOf(1L << 32); // valor maior, mag e bitLength > 31
        boolean exceptionThrown2 = false;
        try {
            bigLarge.intValueExact();
        } catch (ArithmeticException e) {
            exceptionThrown2 = true;
        }
        if (!exceptionThrown2) {
            throw new AssertionError("Expected ArithmeticException for bitLength() > 31");
        }
    }
}
