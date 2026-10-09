/*
 * @test
 * @summary Test java.util.UUID.clockSequence() method behavior
 * @run main UUIDWitupTest_P106371b4f3ca
 */

import java.util.UUID;

public class UUIDWitupTest_P106371b4f3ca {
    public static void main(String[] args) {
        testClockSequenceWithVersion1();
        testClockSequenceWithNonVersion1();
    }

    private static void testClockSequenceWithVersion1() {
        // Construct a UUID with version 1 (time-based) and known leastSigBits
        // version is in mostSigBits bits 12-15 (0x000000000000F000)
        // Set version to 1 by setting bits 12-15 to 0001
        long mostSigBits = 0x0000000000001000L; // version 1
        // Set leastSigBits with clock sequence bits set to 0x1234 (4660 decimal)
        // clock sequence bits are bits 48-61 (14 bits) in leastSigBits
        // So shift 0x1234 left by 48 bits
        long clockSeqBits = 0x1234L << 48;
        long leastSigBits = clockSeqBits;

        UUID uuid = new UUID(mostSigBits, leastSigBits);

        int clockSeq = uuid.clockSequence();

        if (clockSeq != 0x1234) {
            throw new AssertionError("Expected clock sequence 0x1234 but got " + clockSeq);
        }
    }

    private static void testClockSequenceWithNonVersion1() {
        // Construct a UUID with version != 1, e.g. version 4 (random)
        long mostSigBits = 0x0000000000004000L; // version 4
        long leastSigBits = 0L;
        UUID uuid = new UUID(mostSigBits, leastSigBits);

        try {
            uuid.clockSequence();
            throw new AssertionError("Expected UnsupportedOperationException for non-version 1 UUID");
        } catch (UnsupportedOperationException e) {
            // Expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Not a time-based UUID")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
