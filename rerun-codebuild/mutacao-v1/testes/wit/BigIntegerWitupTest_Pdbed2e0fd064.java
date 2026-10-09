/* @test    @summary Test nextProbablePrime() throws ArithmeticException when signum < 0    @run main BigIntegerWitupTest_Pdbed2e0fd064 */

import java.math.BigInteger;

public class BigIntegerWitupTest_Pdbed2e0fd064 {
    public static void main(String[] args) {
        testNextProbablePrimeNegativeSignum();
    }

    private static void testNextProbablePrimeNegativeSignum() {
        BigInteger negativeBigInt = null;
        try {
            // Cria um BigInteger com sinal negativo para disparar a exceção
            negativeBigInt = new BigInteger("-12345");
        } catch (Exception e) {
            throw new RuntimeException("Falha ao criar BigInteger negativo", e);
        }
        try {
            negativeBigInt.nextProbablePrime();
            throw new AssertionError("Expected ArithmeticException for negative signum");
        } catch (ArithmeticException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("start < 0")) {
                throw new AssertionError("Unexpected exception message: " + message);
            }
        }
    }
}
