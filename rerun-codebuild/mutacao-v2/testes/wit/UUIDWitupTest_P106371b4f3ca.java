/*
 * @test
 * @summary Test java.util.UUID.clockSequence() method behavior
 * @run main UUIDWitupTest_P106371b4f3ca
 */

public class UUIDWitupTest_P106371b4f3ca {
    public static void main(String[] args) {
        testClockSequenceWithVersion1();
        testClockSequenceWithNonVersion1();
    }

    private static void testClockSequenceWithVersion1() {
        // Construct a UUID with version 1 (time-based) in mostSigBits
        // version is bits 12-15 of the time_hi_and_version field (bits 48-51 of mostSigBits)
        // Set version to 1: bits 12-15 = 0001
        long mostSigBits = 0L;
        // Set version field to 1: clear bits 12-15, then set bit 12
        mostSigBits |= (1L << 12);

        // leastSigBits with some clock sequence bits set (bits 48-61)
        long leastSigBits = 0x1234000000000000L; // arbitrary with some bits in clock sequence range

        java.util.UUID uuid = new java.util.UUID(mostSigBits, leastSigBits);

        int clockSeq = uuid.clockSequence();

        // The clock sequence is bits 48-61 of leastSigBits, shifted right 48 bits
        int expectedClockSeq = (int)((leastSigBits & 0x3FFF000000000000L) >>> 48);

        if (clockSeq != expectedClockSeq) {
            throw new AssertionError("clockSequence() returned " + clockSeq + ", expected " + expectedClockSeq);
        }
    }

    private static void testClockSequenceWithNonVersion1() {
        // Construct a UUID with version != 1, e.g. version 4 (random)
        long mostSigBits = 0L;
        // Set version field to 4: bits 12-15 = 0100
        mostSigBits |= (4L << 12);

        long leastSigBits = 0L;

        java.util.UUID uuid = new java.util.UUID(mostSigBits, leastSigBits);

        boolean threw = false;
        try {
            uuid.clockSequence();
        } catch (UnsupportedOperationException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Not a time-based UUID")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }

        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException for non-version 1 UUID");
        }
    }
}
