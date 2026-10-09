/* @test
   @summary Testa o método flipBit de BigInteger para entradas válidas e inválidas.
   @run main BigIntegerWitupTest_Pe51a3d38c33f
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_Pe51a3d38c33f {
    public static void main(String[] args) {
        testFlipBitValid();
        testFlipBitNegative();
    }
    
    static void testFlipBitValid() {
        BigInteger bi = BigInteger.valueOf(0);
        BigInteger result = bi.flipBit(0);
        if (result == null) {
            throw new AssertionError("Resultado nulo para flipBit(0)");
        }
        // Testa flipBit com valor positivo
        BigInteger bi2 = BigInteger.valueOf(8); // 1000 binário
        BigInteger result2 = bi2.flipBit(3); // bit 3 (zero-based)
        if (result2 == null) {
            throw new AssertionError("Resultado nulo para flipBit(3)");
        }
    }
    
    static void testFlipBitNegative() {
        try {
            BigInteger bi = BigInteger.valueOf(1);
            bi.flipBit(-1);
            throw new AssertionError("Esperado ArithmeticException para flipBit(-1)");
        } catch (ArithmeticException e) {
            // esperado
        }
        try {
            BigInteger bi = BigInteger.valueOf(10);
            bi.flipBit(-5);
            throw new AssertionError("Esperado ArithmeticException para flipBit(-5)");
        } catch (ArithmeticException e) {
            // esperado
        }
    }
}
