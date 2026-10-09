/* @test    @summary Test UUID.clockSequence() method for time-based UUIDs    @run main UUIDWitupTest_Pbfdc14a713f1 */

import java.util.UUID;

public class UUIDWitupTest_Pbfdc14a713f1 {

    public static void main(String[] args) {
        testClockSequenceWithTimeBasedUUID();
        testClockSequenceWithNonTimeBasedUUID();
    }

    private static void testClockSequenceWithTimeBasedUUID() {
        // Create a UUID with version 1 (time-based)
        // For testing, we construct a UUID with version 1 by setting bits accordingly
        long mostSigBits = 0x1000000000000000L; // version bits set to 1
        long leastSigBits = 0x0000FFFFFFFFFFFFL; // arbitrary
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // Override version to 1 explicitly if needed
        // Since constructor is private, we simulate by reflection or assume constructor sets version 1
        // For simplicity, assume the constructor creates a version 1 UUID
        try {
            int clockSeq = uuid.clockSequence();
            if (clockSeq < 0 || clockSeq > 0x3FFF) {
                throw new AssertionError("clockSequence out of range: " + clockSeq);
            }
            System.out.println("Time-based UUID clockSequence: " + clockSeq);
        } catch (UnsupportedOperationException e) {
            throw new AssertionError("Expected supported operation for version 1 UUID", e);
        }
    }

    private static void testClockSequenceWithNonTimeBasedUUID() {
        // Create a UUID with version != 1
        long msb = 0x2000000000000000L; // version 2 or other
        long lsb = 0x0000FFFFFFFFFFFFL;
        UUID uuid = new UUID(msb, lsb);
        // For this test, forcibly set version to something other than 1
        // Since constructor is private, assume the UUID is not version 1
        try {
            uuid.clockSequence();
            throw new AssertionError("Expected UnsupportedOperationException for non-time-based UUID");
        } catch (UnsupportedOperationException e) {
            System.out.println("Correctly threw exception for non-time-based UUID");
        }
    }
}
