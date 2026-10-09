/* @test
   @summary Verifies that UUID.clockSequence() returns the correct value for a time-based UUID and throws exception otherwise.
   @run main UUIDWitupTest_Pc41eabd0c4b8
 */
import java.util.UUID;
public class UUIDWitupTest_Pc41eabd0c4b8 {
    public static void main(String[] args) {
        // Test with a time-based UUID (version 1)
        long mostSigBits = 0x0000000000000001L;
        long leastSigBits = 0x0000C0FFEE000000L;
        UUID uuidTimeBased = new UUID(mostSigBits, leastSigBits);
        // Force version to 1 for testing
        // Note: In real scenario, UUID version is determined by bits, but here we simulate
        // Since constructor is private, we assume we have a UUID with version 1
        // For testing, we can mock or simulate by reflection if needed, but here we assume direct creation
        // For simplicity, we test the method behavior with a UUID that is not version 1
        try {
            uuidTimeBased.clockSequence();
            throw new AssertionError("Expected UnsupportedOperationException for non-version 1 UUID");
        } catch (UnsupportedOperationException e) {
            // expected
        }
        // Test with a UUID with version 1 (simulate by setting bits accordingly)
        long msb = 0x1000000000000000L; // version bits set to 1
        long lsb = 0xC000000000000000L; // variant bits set
        UUID v1uuid = new UUID(msb, lsb);
        int expectedClockSeq = (int)((lsb & 0x3FFF000000000000L) >>> 48);
        int actualClockSeq = v1uuid.clockSequence();
        if (actualClockSeq != expectedClockSeq) {
            throw new AssertionError("clockSequence() returned " + actualClockSeq + ", expected " + expectedClockSeq);
        }
    }
}
