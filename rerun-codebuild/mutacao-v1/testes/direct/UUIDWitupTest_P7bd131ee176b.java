/* @test    @summary Test UUID.timestamp() for time-based UUIDs and non-time-based UUIDs    @run main UUIDWitupTest_P7bd131ee176b */

import java.util.UUID;

public class UUIDWitupTest_P7bd131ee176b {

    public static void main(String[] args) {
        testTimeBasedUUID();
        testNonTimeBasedUUID();
    }

    private static void testTimeBasedUUID() {
        // Create a UUID with version 1 (time-based)
        // Since the constructor is private, we simulate a time-based UUID by reflection or assume a UUID with version 1.
        // For testing, we can create a UUID with version 1 by setting bits accordingly.
        // Here, we simulate by creating a UUID with mostSigBits and leastSigBits set to values with version bits set to 1.
        long msb = 0x1000000000000000L; // version bits set to 1 in bits 12-15
        long lsb = 0L;
        UUID uuid = new UUID(msb, lsb);
        // Overwrite version bits to 1
        long versionMask = 0xF000L;
        long versionBits = 0x1000L; // version 1
        long newMostSigBits = (uuid.getMostSignificantBits() & ~versionMask) | (versionBits << 48);
        uuid = new UUID(newMostSigBits, uuid.getLeastSignificantBits());
        try {
            long timestamp = uuid.timestamp();
            System.out.println("Time-based UUID timestamp: " + timestamp);
        } catch (UnsupportedOperationException e) {
            throw new RuntimeException("Failed to get timestamp from time-based UUID", e);
        }
    }

    private static void testNonTimeBasedUUID() {
        // Create a UUID with version 4 (random)
        UUID uuid = UUID.randomUUID();
        try {
            uuid.timestamp();
            throw new AssertionError("Expected UnsupportedOperationException for non-time-based UUID");
        } catch (UnsupportedOperationException e) {
            System.out.println("Correctly caught exception for non-time-based UUID: " + e.getMessage());
        }
    }
}
