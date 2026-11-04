/*
 * @test
 * @summary Test java.util.UUID.timestamp() method behavior including exception for non-time-based UUIDs
 * @run main UUIDWitupTest
 */

import java.util.UUID;

public class UUIDWitupTest {
    public static void main(String[] args) {
        testTimestampWithVersion1();
        testTimestampWithNonVersion1();
    }

    private static void testTimestampWithVersion1() {
        // version() == 1 means ((mostSigBits >>> 12) & 0x0f) == 1
        // Construct UUID with version 1 in mostSigBits
        long version1MostSigBits = 0L;
        // Set version field (bits 12-15) to 1
        version1MostSigBits |= (1L << 12);
        // Other bits can be zero
        long leastSigBits = 0L;
        UUID uuid = new UUID(version1MostSigBits, leastSigBits);

        // The timestamp method should return a long without throwing
        long ts = uuid.timestamp();
        // The timestamp is composed from parts of mostSigBits, so it should be >= 0
        if (ts < 0) {
            throw new AssertionError("timestamp() returned negative value for version 1 UUID");
        }
    }

    private static void testTimestampWithNonVersion1() {
        // version() != 1 means ((mostSigBits >>> 12) & 0x0f) != 1
        // Use version 4 (random) for example
        long version4MostSigBits = 0L;
        // Set version field (bits 12-15) to 4
        version4MostSigBits |= (4L << 12);
        long leastSigBits = 0L;
        UUID uuid = new UUID(version4MostSigBits, leastSigBits);

        boolean threw = false;
        try {
            uuid.timestamp();
        } catch (UnsupportedOperationException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Not a time-based UUID")) {
                throw new AssertionError("UnsupportedOperationException message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new AssertionError("timestamp() did not throw UnsupportedOperationException for non-version 1 UUID");
        }
    }
}
