/* @test
   @summary Testa o método timestamp() de UUID para comportamentos válidos e exceções.
   @run main UUIDWitupTest_P5c57daf8c638
 */
import java.util.UUID;
public class UUIDWitupTest_P5c57daf8c638 {
    public static void main(String[] args) {
        testTimestampWithVersion1();
        testTimestampWithNonVersion1();
    }

    private static void testTimestampWithVersion1() {
        // Cria um UUID de versão 1 válido (mostSigBits com bits de versão 1)
        long mostSigBits = 0x1000000000000000L; // bits de versão 1 na posição correta
        long leastSigBits = 0L;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // Verifica se o método não lança exceção e retorna valor esperado
        long timestamp = uuid.timestamp();
        System.out.println("timestamp (versão 1): " + timestamp);
    }

    private static void testTimestampWithNonVersion1() {
        // Cria um UUID de versão 2 (bits de versão diferentes de 1)
        long mostSigBits = 0x2000000000000000L; // bits de versão 2
        long leastSigBits = 0L;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        try {
            uuid.timestamp();
            throw new AssertionError("Esperado UnsupportedOperationException para UUID não versão 1");
        } catch (UnsupportedOperationException e) {
            System.out.println("Exceção esperada capturada: " + e.getMessage());
        }
    }
}
