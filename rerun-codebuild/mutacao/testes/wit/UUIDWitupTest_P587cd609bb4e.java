/* @test    @summary Tests for java.util.UUID class, focusing on the node() method behavior.    @run main UUIDWitupTest_P587cd609bb4e */

import java.util.UUID;

public class UUIDWitupTest_P587cd609bb4e {

    public static void main(String[] args) {
        testNodeMethodWithTimeBasedUUID();
        testNodeMethodWithNonTimeBasedUUID();
    }

    private static void testNodeMethodWithTimeBasedUUID() {
        // Create a UUID with version 1 (time-based)
        // For testing, we simulate a UUID with version 1 by setting mostSigBits accordingly
        long mostSigBits = 0x1000000000000000L; // version bits set to 1 in bits 12-15
        long leastSigBits = 0x0000FFFFFFFFFFFFL;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // The node() method should return leastSigBits & 0x0000FFFFFFFFFFFFL
        long expectedNode = leastSigBits & 0x0000FFFFFFFFFFFFL;
        long actualNode = uuid.node();
        if (actualNode != expectedNode) {
            throw new AssertionError("Expected node: " + expectedNode + ", but got: " + actualNode);
        }
    }

    private static void testNodeMethodWithNonTimeBasedUUID() {
        // Create a UUID with version 2 (non time-based)
        long mostSigBits = 0x2000000000000000L; // version bits set to 2
        long leastSigBits = 0x0000FFFFFFFFFFFFL;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // According to the baseline WIT, this should throw UnsupportedOperationException
        try {
            uuid.node();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
    }
}
