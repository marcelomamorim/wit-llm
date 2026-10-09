/* @test
   @summary Testa o método clockSequence() da classe java.util.UUID.
   @run main UUIDWitupTest_Pc41eabd0c4b8
 */
import java.util.UUID;
public class UUIDWitupTest_Pc41eabd0c4b8 {
    public static void main(String[] args) {
        testClockSequenceTimeBased();
        testClockSequenceNonTimeBased();
    }
    private static void testClockSequenceTimeBased() {
        UUID uuid = new UUID(0x1000000000000000L, 0x0000FFFFFFFFFFFFL); // version 1
        int result = uuid.clockSequence();
        if (result != (int)((uuid.leastSigBits & 0x3FFF000000000000L) >>> 48)) {
            throw new AssertionError("Unexpected clockSequence for time-based UUID");
        }
    }
    private static void testClockSequenceNonTimeBased() {
        UUID uuid = new UUID(0x2000000000000000L, 0x0000FFFFFFFFFFFFL); // não é versão 1
        try {
            uuid.clockSequence();
            throw new AssertionError("Expected UnsupportedOperationException for non-time-based UUID");
        } catch (UnsupportedOperationException e) {
            // esperado
        }
    }
}
