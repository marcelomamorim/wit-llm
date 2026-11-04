/*
 * @test
 * @summary Checks UUID.clockSequence() for time-based and non-time-based UUIDs
 * @run main UUIDWitupTest
 */

public class UUIDWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testClockSequenceVersion1() {
        long mostSigBits = 0x0000000000001000L;
        long leastSigBits = 0x8000000000000000L | (0x1234L << 48);
        java.util.UUID uuid = new java.util.UUID(mostSigBits, leastSigBits);
        check(uuid.version() == 1, "expected version 1");
        check(uuid.clockSequence() == 0x1234, "expected clock sequence 0x1234 but was " + uuid.clockSequence());
    }

    private static void testClockSequenceMasksVariantBits() {
        long mostSigBits = 0x0000000000001000L;
        long leastSigBits = 0xFFFF000000000000L | 0x0000FFFFFFFFFFFFL;
        java.util.UUID uuid = new java.util.UUID(mostSigBits, leastSigBits);
        check(uuid.clockSequence() == 0x3FFF, "expected masked clock sequence 0x3FFF but was " + uuid.clockSequence());
    }

    private static void testClockSequenceZero() {
        long mostSigBits = 0x0000000000001000L;
        long leastSigBits = 0x8000000000000000L;
        java.util.UUID uuid = new java.util.UUID(mostSigBits, leastSigBits);
        check(uuid.clockSequence() == 0, "expected clock sequence 0 but was " + uuid.clockSequence());
    }

    private static void testClockSequenceNonVersion1Throws() {
        long mostSigBits = 0x0000000000004000L;
        long leastSigBits = 0x8000000000000000L | (0x1234L << 48);
        java.util.UUID uuid = new java.util.UUID(mostSigBits, leastSigBits);
        boolean thrown = false;
        try {
            uuid.clockSequence();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "expected UnsupportedOperationException for non-time-based UUID");
    }

    public static void main(String[] args) {
        testClockSequenceVersion1();
        testClockSequenceMasksVariantBits();
        testClockSequenceZero();
        testClockSequenceNonVersion1Throws();
    }
}
