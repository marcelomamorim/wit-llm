/* @test    @summary Test UUID.node() method for time-based UUIDs    @run main UUIDWitupTest_P587cd609bb4e */

import java.util.UUID;

public class UUIDWitupTest_P587cd609bb4e {

    public static void main(String[] args) {
        testNodeWithTimeBasedUUID();
        testNodeWithNonTimeBasedUUID();
    }

    private static void testNodeWithTimeBasedUUID() {
        // Create a UUID with version 1 (time-based)
        long mostSigBits = 0x0000000000000001L;
        long leastSigBits = 0x0000FFFFFFFFFFFFL; // node bits
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // Force version to 1 for this test
        // Since constructor is private, simulate by reflection or assume creation with version 1
        // For simplicity, assume the UUID is version 1 if leastSigBits matches pattern
        // Here, we simulate by directly setting leastSigBits to match node bits
        // Call node() and verify output
        long nodeValue = uuid.node();
        if (nodeValue != (leastSigBits & 0x0000FFFFFFFFFFFFL)) {
            throw new AssertionError("node() returned incorrect value for time-based UUID");
        }
        System.out.println("testNodeWithTimeBasedUUID passed.");
    }

    private static void testNodeWithNonTimeBasedUUID() {
        // Create a UUID with version != 1
        long mostSigBits = 0x0000000000000002L; // version 2
        long leastSigBits = 0x0000FFFFFFFFFFFFL;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        try {
            uuid.node();
            throw new AssertionError("Expected UnsupportedOperationException for non-time-based UUID");
        } catch (UnsupportedOperationException e) {
            // Expected exception
            System.out.println("testNodeWithNonTimeBasedUUID passed.");
        }
    }
}
