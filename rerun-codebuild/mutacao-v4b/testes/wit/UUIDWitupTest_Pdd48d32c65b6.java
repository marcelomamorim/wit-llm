/* @test
   @summary Testa o método node() da classe java.util.UUID.
   @run main UUIDWitupTest_Pdd48d32c65b6
 */
import java.util.UUID;
public class UUIDWitupTest_Pdd48d32c65b6 {
    public static void main(String[] args) {
        testNodeMethodTimeBasedUUID();
        testNodeMethodNonTimeBasedUUID();
    }

    private static void testNodeMethodTimeBasedUUID() {
        // Cria um UUID de versão 1 (time-based) com valores arbitrários
        long mostSigBits = 0x1000000000000000L; // versão 1 (bits 12-15 = 0001)
        long leastSigBits = 0x0000FFFFFFFFFFFFL;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // Verifica se o método node() retorna o valor esperado
        long nodeValue = uuid.node();
        if (nodeValue != (leastSigBits & 0x0000FFFFFFFFFFFFL)) {
            throw new AssertionError("node() retornou valor inesperado para UUID de versão 1");
        }
    }

    private static void testNodeMethodNonTimeBasedUUID() {
        // Cria um UUID de versão 4 (aleatório) com valores arbitrários
        long mostSigBits = 0x2000000000000000L; // versão 2 (bits 12-15 = 0010)
        long leastSigBits = 0x123456789ABCDEF0L;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // Espera-se que o método node() lance UnsupportedOperationException
        try {
            uuid.node();
            throw new AssertionError("Esperado UnsupportedOperationException para UUID de versão não 1");
        } catch (UnsupportedOperationException e) {
            // esperado
        }
    }
}
