/* @test    @summary Test UUID.timestamp() method for supported and unsupported variants    @run main UUIDWitupTest_P7bd131ee176b */

import java.util.UUID;

public class UUIDWitupTest_P7bd131ee176b {

    public static void main(String[] args) {
        testTimestampSupported();
        testTimestampUnsupported();
    }

    private static void testTimestampSupported() {
        // Cria um UUID de versão 1 (time-based)
        long mostSigBits = 0x1000000000000000L; // versão 1
        long leastSigBits = 0x0000000000000000L;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // Força o método a retornar um valor válido
        try {
            long ts = uuid.timestamp();
            if (ts != (mostSigBits & 0x0FFFL) << 48 | ((mostSigBits >> 16) & 0x0FFFFL) << 32 | (mostSigBits >>> 32)) {
                throw new AssertionError("timestamp() retornou valor inesperado para UUID de versão 1");
            }
        } catch (UnsupportedOperationException e) {
            throw new AssertionError("timestamp() lançou exceção inesperada para UUID de versão 1", e);
        }
    }

    private static void testTimestampUnsupported() {
        // Cria um UUID de versão diferente de 1
        long mostSigBits = 0x2000000000000000L; // versão 2
        long leastSigBits = 0x0000000000000000L;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // Espera que lance UnsupportedOperationException
        try {
            uuid.timestamp();
            throw new AssertionError("timestamp() não lançou exceção para UUID de versão não 1");
        } catch (UnsupportedOperationException e) {
            // Esperado
        }
    }
}
