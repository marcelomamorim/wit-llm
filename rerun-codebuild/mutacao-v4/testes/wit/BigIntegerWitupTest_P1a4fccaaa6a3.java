/* @test
   @summary Testa o método byteValueExact() de BigInteger para comportamentos observáveis.
   @run main BigIntegerWitupTest_P1a4fccaaa6a3
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_P1a4fccaaa6a3 {
    public static void main(String[] args) {
        testByteValueExactWithinRange();
        testByteValueExactOutOfRange();
    }
    private static void testByteValueExactWithinRange() {
        // Cria BigInteger com valor dentro do intervalo de byte
        BigInteger bi = BigInteger.valueOf(127); // Byte.MAX_VALUE
        byte result = bi.byteValueExact();
        if (result != 127) {
            throw new AssertionError("Expected 127, got " + result);
        }
        bi = BigInteger.valueOf(-128); // Byte.MIN_VALUE
        result = bi.byteValueExact();
        if (result != -128) {
            throw new AssertionError("Expected -128, got " + result);
        }
    }
    private static void testByteValueExactOutOfRange() {
        // Cria BigInteger com valor fora do intervalo de byte
        BigInteger bi = BigInteger.valueOf(128); // fora do limite superior
        try {
            bi.byteValueExact();
            throw new AssertionError("Expected ArithmeticException for value 128");
        } catch (ArithmeticException e) {
            // esperado
        }
        bi = BigInteger.valueOf(-129); // fora do limite inferior
        try {
            bi.byteValueExact();
            throw new AssertionError("Expected ArithmeticException for value -129");
        } catch (ArithmeticException e) {
            // esperado
        }
    }
}
