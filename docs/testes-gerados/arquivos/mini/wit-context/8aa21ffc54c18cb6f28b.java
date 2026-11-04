/*
 * @test
 * @summary Test java.util.UUID.node() method behavior including exception for non-time-based UUIDs
 * @run main java.util.UUIDWitupTest
 */

public class UUIDWitupTest {
    public static void main(String[] args) {
        testNodeWithTimeBasedUUID();
        testNodeWithNonTimeBasedUUID();
    }

    private static void testNodeWithTimeBasedUUID() {
        // version() == 1 means time-based UUID
        // Construct UUID with version 1 in mostSigBits
        // version is bits 12-15 of mostSigBits (0x000000000000F000)
        // Set version to 1 (0x1 << 12 = 0x1000)
        long mostSigBits = 0x0000000000001000L; // version 1
        long leastSigBits = 0x0000FFFFFFFFFFFFL; // arbitrary node bits
        java.util.UUID uuid = new java.util.UUID(mostSigBits, leastSigBits);

        long node = uuid.node();
        if (node != (leastSigBits & 0x0000FFFFFFFFFFFFL)) {
            throw new AssertionError("node() returned unexpected value: " + node);
        }
    }

    private static void testNodeWithNonTimeBasedUUID() {
        // version() != 1 should throw UnsupportedOperationException
        // Set version to 2 (0x2 << 12 = 0x2000)
        long mostSigBits = 0x0000000000002000L; // version 2
        long leastSigBits = 0L;
        java.util.UUID uuid = new java.util.UUID(mostSigBits, leastSigBits);

        boolean thrown = false;
        try {
            uuid.node();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Not a time-based UUID")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
