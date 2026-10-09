/* @test    @summary Test UUID.timestamp() method for time-based UUIDs    @run main UUIDWitupTest_Pb28324c7ae30 */

import java.util.UUID;

public class UUIDWitupTest_Pb28324c7ae30 {

    public static void main(String[] args) {
        testTimestampWithTimeBasedUUID();
        testTimestampWithNonTimeBasedUUID();
    }

    private static void testTimestampWithTimeBasedUUID() {
        // Create a time-based UUID (version 1) with specific mostSigBits and leastSigBits
        long msb = 0x0000000000001000L; // version 1 indicator in bits 12-15
        long lsb = 0x8000000000000000L; // variant bits
        UUID uuid = new UUID(msb, lsb);
        // Override version to 1 explicitly for test
        // Note: In real scenario, UUID should be generated as version 1, but here we simulate
        // For testing, assume the UUID is version 1
        // Since the method checks version() == 1, we need to ensure version bits are set accordingly
        // But as version() is not shown, we assume the UUID is correctly versioned
        // For the purpose of this test, we proceed
        long timestamp = uuid.timestamp();
        // The timestamp should be computed based on msb
        long expectedTimestamp = (msb & 0x0FFFL) << 48 |
                                 ((msb >> 16) & 0x0FFFFL) << 32 |
                                 (msb >>> 32);
        assert timestamp == expectedTimestamp : "Timestamp mismatch for time-based UUID";
        System.out.println("testTimestampWithTimeBasedUUID passed.");
    }

    private static void testTimestampWithNonTimeBasedUUID() {
        // Create a non-time-based UUID (version 4) with specific mostSigBits and leastSigBits
        long msb = 0x0000000000004000L; // version 4 indicator
        long lsb = 0x8000000000000000L; // variant bits
        UUID uuid = new UUID(msb, lsb);
        try {
            uuid.timestamp();
            assert false : "Expected UnsupportedOperationException for non-time-based UUID";
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected exception for non-time-based UUID.");
        }
        System.out.println("testTimestampWithNonTimeBasedUUID passed.");
    }
}
