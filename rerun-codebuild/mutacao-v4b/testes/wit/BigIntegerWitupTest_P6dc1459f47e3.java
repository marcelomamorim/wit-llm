/* @test
   @summary Testa o método clearBit de BigInteger para diferentes valores de n.
   @run main BigIntegerWitupTest_P6dc1459f47e3
 */
import java.math.BigInteger;
public class BigIntegerWitupTest_P6dc1459f47e3 {
    public static void main(String[] args) {
        testClearBitNegative();
        testClearBitZero();
        testClearBitPositive();
        testClearBitLarge();
    }
    
    static void testClearBitNegative() {
        try {
            BigInteger bi = BigInteger.valueOf(12345);
            bi.clearBit(-1);
            throw new AssertionError("Expected ArithmeticException for negative n");
        } catch (ArithmeticException e) {
            // esperado
        }
    }
    
    static void testClearBitZero() {
        BigInteger bi = BigInteger.valueOf(12345);
        BigInteger result = bi.clearBit(0);
        BigInteger expected = BigInteger.valueOf(12344); // 12345 com o bit 0 limpo
        if (!result.equals(expected)) {
            throw new AssertionError("clearBit(0) failed: " + result);
        }
    }
    
    static void testClearBitPositive() {
        BigInteger bi = new BigInteger("1024"); // 2^10
        BigInteger result = bi.clearBit(10); // limpa o bit 10
        BigInteger expected = BigInteger.valueOf(0); // 1024 com bit 10 limpo é zero
        if (!result.equals(expected)) {
            throw new AssertionError("clearBit(10) failed: " + result);
        }
    }
    
    static void testClearBitLarge() {
        BigInteger bi = new BigInteger("123456789");
        BigInteger result = bi.clearBit(20); // limpa o bit 20
        // calcula o esperado manualmente
        BigInteger expected = bi;
        int n = 20;
        int intNum = n >>> 5;
        int[] mag = bi.toByteArray().length > 0 ? new int[(bi.bitLength() + 31) >>> 5] : new int[0];
        // Como não podemos acessar internamente, vamos verificar se o resultado tem o bit 20 limpo
        boolean bitSet = result.testBit(20);
        if (bitSet) {
            throw new AssertionError("Bit 20 was not cleared");
        }
    }
}
