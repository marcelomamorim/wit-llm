/* @test    @summary Tests for java.util.UUID.clockSequence() method behavior.    @run main UUIDWitupTest_Pa1393361250c */

import java.util.UUID;

public class UUIDWitupTest_Pa1393361250c {

    public static void main(String[] args) {
        testClockSequenceWithTimeBasedUUID();
        testClockSequenceWithNonTimeBasedUUID();
    }

    private static void testClockSequenceWithTimeBasedUUID() {
        // Cria um UUID de exemplo com versão 1 (time-based)
        // Como não há construtor público, usamos um método de fábrica ou reflexão, mas aqui, para simplicidade,
        // assumimos um UUID de exemplo válido com versão 1.
        // Como o método é dependente do estado interno, criamos um UUID manualmente com bits apropriados.
        long mostSigBits = 0x0001000000000000L; // versão 1 (bits 12-15 = 0001)
        long leastSigBits = 0x0000C00000000000L; // bits de variante e outros
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        int result = uuid.clockSequence();
        // Como o leastSigBits tem bits de variante e sequência de relógio, esperamos um valor válido
        // Para UUIDs de versão 1, o clockSequence deve ser derivado dos bits 48-63 do leastSigBits
        int expected = (int)((leastSigBits & 0x3FFF000000000000L) >>> 48);
        assert result == expected : "Expected clockSequence to be " + expected + " but was " + result;
    }

    private static void testClockSequenceWithNonTimeBasedUUID() {
        // Cria um UUID com versão diferente de 1
        long mostSigBits = 0x0002000000000000L; // versão 2
        long leastSigBits = 0x0000C00000000000L;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        try {
            int result = uuid.clockSequence();
            // Espera que a exceção seja lançada
            assert false : "Expected UnsupportedOperationException to be thrown";
        } catch (UnsupportedOperationException e) {
            assert e.getMessage().equals("Not a time-based UUID") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
