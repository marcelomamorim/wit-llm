/* @test
   @summary Testa o método intValueExact() de BigInteger para comportamentos observáveis.
   @run main BigIntegerWitupTest_P9aa83e3e9efa
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_P9aa83e3e9efa {
    public static void main(String[] args) {
        testWithinRange();
        testOutOfRange();
    }
    private static void testWithinRange() {
        BigInteger bi = BigInteger.valueOf(12345L);
        int result = bi.intValueExact();
        if (result != 12345) {
            throw new AssertionError("Expected 12345, got " + result);
        }
    }
    private static void testOutOfRange() {
        // Cria um BigInteger com valor maior que o limite de int
        // Para isso, usamos uma magnitude que, ao ser convertida, ultrapassa o limite
        byte[] mag = { (byte)0x80, 0, 0, 0 }; // valor grande, com o bit mais significativo setado
        BigInteger bi = new BigInteger(1, mag);
        try {
            bi.intValueExact();
            throw new AssertionError("Expected ArithmeticException for out of range value");
        } catch (ArithmeticException e) {
            // esperado
        }
    }
}
