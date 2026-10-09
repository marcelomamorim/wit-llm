/* @test    @summary Teste do método longValueExact para verificar comportamento esperado e exceções.    @run main BigIntegerWitupTest_P8a6bbc8084a9 */

import java.math.BigInteger;

public class BigIntegerWitupTest_P8a6bbc8084a9 {

    public static void main(String[] args) {
        testLongValueExactNormal();
        testLongValueExactOutOfRange();
    }

    private static void testLongValueExactNormal() {
        // Cria um BigInteger dentro do intervalo de long
        BigInteger bi = BigInteger.valueOf(123456789L);
        long result = bi.longValueExact();
        if (result != 123456789L) {
            throw new AssertionError("Falha: valor esperado 123456789, obtido " + result);
        }
    }

    private static void testLongValueExactOutOfRange() {
        // Cria um BigInteger que excede o intervalo de long
        // Para isso, cria um BigInteger com comprimento de magnitude maior que 2 ou bitLength > 63
        // Exemplo: BigInteger com magnitude grande
        byte[] mag = { (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x7F };
        BigInteger bi = new BigInteger(1, mag); // signum positivo
        // Assegura que o bitLength > 63 ou mag.length > 2
        if (bi.bitLength() <= 63 && mag.length <= 2) {
            throw new RuntimeException("Configuração inválida para teste: bi não excede long");
        }
        try {
            bi.longValueExact();
            throw new AssertionError("Falha: esperado ArithmeticException para valor fora do range de long");
        } catch (ArithmeticException e) {
            // Esperado
        }
    }
}
