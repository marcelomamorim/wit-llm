/* @test    @summary Testa o método node() da classe java.util.UUID para comportamentos observáveis.    @run main UUIDWitupTest_P959d1cef71e7 */

import java.util.UUID;

public class UUIDWitupTest_P959d1cef71e7 {

    public static void main(String[] args) {
        testNodeMethodWithVersion1();
        testNodeMethodWithOtherVersions();
    }

    private static void testNodeMethodWithVersion1() {
        // Cria um UUID de versão 1 válido (mostSigBits com versão 1)
        long mostSigBits = 0x1000000000000000L; // versão 1 no campo adequado
        long leastSigBits = 0x0000FFFFFFFFFFFFL;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        long nodeValue = uuid.node();
        // O valor retornado deve ser igual ao leastSigBits & 0x0000FFFFFFFFFFFFL
        if (nodeValue != (leastSigBits & 0x0000FFFFFFFFFFFFL)) {
            throw new AssertionError("Unexpected node value for version 1 UUID");
        }
    }

    private static void testNodeMethodWithOtherVersions() {
        // Cria um UUID com versão diferente de 1
        long mostSigBits = 0x2000000000000000L; // versão 2 (não é 1)
        long leastSigBits = 0x0000FFFFFFFFFFFFL;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        try {
            uuid.node();
            throw new AssertionError("Expected UnsupportedOperationException for non-version 1 UUID");
        } catch (UnsupportedOperationException e) {
            // esperado
        }
    }
}
