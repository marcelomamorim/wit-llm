/* @test
   @summary Verifica o método longValueExact() de BigInteger para comportamentos observáveis.
   @run main BigIntegerWitupTest_Pdd60f5b31f3b
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_Pdd60f5b31f3b {
    public static void main(String[] args) {
        testLongValueExactWithinRange();
        testLongValueExactOutOfRange();
    }
    private static void testLongValueExactWithinRange() {
        // Cria BigInteger com valor dentro do range de long
        BigInteger bi = BigInteger.valueOf(Long.MAX_VALUE);
        long result = bi.longValueExact();
        if (result != Long.MAX_VALUE) {
            throw new AssertionError("Expected Long.MAX_VALUE, got " + result);
        }
    }
    private static void testLongValueExactOutOfRange() {
        // Cria BigInteger com valor que deve lançar ArithmeticException
        // Para isso, cria um BigInteger com magnitude maior que o long
        byte[] mag = {0, 1}; // magnitude maior que Long.MAX_VALUE
        BigInteger bi = new BigInteger(1, mag);
        try {
            bi.longValueExact();
            throw new AssertionError("Expected ArithmeticException not thrown");
        } catch (ArithmeticException e) {
            // esperado
        }
    }
}
