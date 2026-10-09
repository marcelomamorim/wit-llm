/* @test
   @summary Testa o método generateSeed de SecureRandom para entrada inválida.
   @run main SecureRandomWitupTest_Pbfa75d16bb60
 */
import java.security.SecureRandom;
import java.util.Arrays;

public class SecureRandomWitupTest_Pbfa75d16bb60 {
    public static void main(String[] args) {
        testGenerateSeedNegative();
        testGenerateSeedPositive();
    }

    private static void testGenerateSeedNegative() {
        try {
            SecureRandom sr = new SecureRandom();
            byte[] result = sr.generateSeed(-1);
            System.err.println("Falha: Esperado IllegalArgumentException para -1, mas retornou: " + Arrays.toString(result));
            throw new AssertionError("Expected IllegalArgumentException for generateSeed(-1)");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    private static void testGenerateSeedPositive() {
        try {
            SecureRandom sr = new SecureRandom();
            byte[] result = sr.generateSeed(10);
            if (result == null || result.length != 10) {
                throw new AssertionError("generateSeed(10) retornou resultado inválido: " + Arrays.toString(result));
            }
        } catch (Exception e) {
            throw new AssertionError("Erro inesperado em generateSeed(10): " + e, e);
        }
    }
}
