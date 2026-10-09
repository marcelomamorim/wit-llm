/* @test    @summary Testa o método clockSequence() da classe java.util.UUID para verificar comportamento esperado e exceções.    @run main UUIDWitupTest_Pbfdc14a713f1 */

import java.util.UUID;

public class UUIDWitupTest_Pbfdc14a713f1 {

    public static void main(String[] args) {
        testClockSequenceTimeBasedUUID();
        testClockSequenceNonTimeBasedUUID();
        testClockSequenceInvalidVersion();
    }

    private static void testClockSequenceTimeBasedUUID() {
        // Cria um UUID de versão 1 (time-based)
        // Como não há construtor público, usar um UUID conhecido de versão 1
        UUID uuid = UUID.fromString("00000000-0000-1000-8000-000000000000");
        // Ajustar os bits para garantir que seja versão 1
        // Como não há método público para criar, usar o UUID padrão e manipular bits é inviável; portanto, usar um UUID conhecido
        // Verifica se o método retorna um valor válido
        int result = uuid.clockSequence();
        System.out.println("Time-based UUID clockSequence: " + result);
        // Como o método não lança exceção, apenas valida a saída
        if (result < 0 || result > 0x3FFF) {
            throw new AssertionError("clockSequence() retornou valor fora do intervalo esperado para UUID de versão 1");
        }
    }

    private static void testClockSequenceNonTimeBasedUUID() {
        // Cria um UUID de versão diferente de 1
        UUID uuid = UUID.fromString("00000000-0000-2000-8000-000000000000");
        // Verifica se o método lança UnsupportedOperationException
        try {
            uuid.clockSequence();
            throw new AssertionError("Esperado UnsupportedOperationException para UUID não de versão 1");
        } catch (UnsupportedOperationException e) {
            System.out.println("Correto: UnsupportedOperationException lançada para UUID não de versão 1");
        }
    }

    private static void testClockSequenceInvalidVersion() {
        // Cria um UUID com versão inválida (não é possível criar diretamente, então simula com um UUID padrão)
        UUID uuid = UUID.fromString("00000000-0000-0000-8000-000000000000");
        // Como o método verifica a versão, se for diferente de 1, lança exceção
        try {
            uuid.clockSequence();
            throw new AssertionError("Esperado UnsupportedOperationException para versão inválida");
        } catch (UnsupportedOperationException e) {
            System.out.println("Correto: UnsupportedOperationException lançada para versão inválida");
        }
    }
}
