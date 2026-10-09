/* @test    @summary Testa o método node() da classe java.util.UUID para verificar comportamento esperado e exceções.    @run main UUIDWitupTest_P9016fa6e1858 */

import java.util.UUID;

public class UUIDWitupTest_P9016fa6e1858 {

    public static void main(String[] args) {
        testNodeMethodTimeBasedUUID();
        testNodeMethodNonTimeBasedUUID();
    }

    private static void testNodeMethodTimeBasedUUID() {
        // Cria um UUID de versão 1 (time-based) com valores arbitrários
        long mostSigBits = 0x1000000000000000L; // versão 1 (bits 12-15 do mostSigBits)
        long leastSigBits = 0x0000FFFFFFFFFFFFL;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // Verifica se o método node() retorna o valor esperado
        long nodeValue = uuid.node();
        if (nodeValue != leastSigBits) {
            throw new AssertionError("node() retornou valor inesperado para UUID de versão 1: " + nodeValue);
        }
    }

    private static void testNodeMethodNonTimeBasedUUID() {
        // Cria um UUID de versão 4 (random) com valores arbitrários
        long mostSigBits = 0x2000000000000000L; // não é versão 1
        long leastSigBits = 0x0000FFFFFFFFFFFFL;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // Espera-se que o método node() lance UnsupportedOperationException
        try {
            uuid.node();
            throw new AssertionError("Esperado UnsupportedOperationException para UUID não de versão 1, mas não foi lançada.");
        } catch (UnsupportedOperationException e) {
            // Test passou, exceção esperada
        }
    }
}
