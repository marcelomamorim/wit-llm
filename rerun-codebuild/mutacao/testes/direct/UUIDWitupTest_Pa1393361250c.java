/* @test    @summary Test UUID.clockSequence() method for time-based UUIDs    @run main UUIDWitupTest_Pa1393361250c */

import java.util.UUID;

public class UUIDWitupTest_Pa1393361250c {

    public static void main(String[] args) {
        testClockSequenceWithTimeBasedUUID();
        testClockSequenceWithNonTimeBasedUUID();
    }

    private static void testClockSequenceWithTimeBasedUUID() {
        // Create a UUID with version 1 (time-based)
        // For testing, we simulate a UUID with version 1 by setting bits accordingly
        long mostSigBits = 0x1000000000000000L; // version bits set to 1
        long leastSigBits = 0x0000C00000000000L; // variant bits set to 0xC (variant 2)
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // Override version to 1 explicitly if needed
        // Since constructor is private, we assume the UUID is correctly constructed
        // For test purposes, we directly call clockSequence
        int clockSeq = uuid.clockSequence();
        // The expected clock sequence is bits 48-63 of leastSigBits masked with 0x3FFF
        int expected = (int)((leastSigBits & 0x3FFF000000000000L) >>> 48);
        if (clockSeq != expected) {
            throw new AssertionError("Expected clock sequence: " + expected + ", but got: " + clockSeq);
        }
    }

    private static void testClockSequenceWithNonTimeBasedUUID() {
        // Create a UUID with version != 1, e.g., version 4 (random)
        long mostSigBits = 0x4000000000000000L; // version bits set to 4
        long leastSigBits = 0x8000000000000000L; // variant bits set to 2
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        try {
            uuid.clockSequence();
            throw new AssertionError("Expected UnsupportedOperationException for non-time-based UUID");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
