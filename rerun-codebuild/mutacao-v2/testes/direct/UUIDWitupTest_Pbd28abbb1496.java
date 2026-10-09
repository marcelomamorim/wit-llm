/*
 * @test
 * @summary Test java.util.UUID.node() method behavior
 * @run main UUIDWitupTest_Pbd28abbb1496
 */

import java.util.UUID;

public class UUIDWitupTest_Pbd28abbb1496 {
    public static void main(String[] args) {
        testNodeWithVersion1();
        testNodeWithNonVersion1Throws();
    }

    private static void testNodeWithVersion1() {
        // Construct a UUID with version 1 (time-based)
        // version is bits 12-15 of mostSigBits, so set version to 1
        long msb = 0L;
        // Set version to 1: clear bits 12-15 and set bit 12
        msb &= ~0x000000000000F000L;
        msb |= 0x0000000000001000L;

        // leastSigBits with some node bits
        long nodeBits = 0x0000FFFFFFFFFFFFL & 0x123456789ABCDEFL;
        long lsb = nodeBits;

        UUID uuid = new UUID(msb, lsb);

        // The node() method should return the lower 48 bits of leastSigBits
        long node = uuid.node();
        if (node != nodeBits) {
            throw new AssertionError("Expected node bits: " + Long.toHexString(nodeBits) + ", got: " + Long.toHexString(node));
        }
    }

    private static void testNodeWithNonVersion1Throws() {
        // Construct a UUID with version != 1
        long msb = 0L;
        // Set version to 4 (random-based UUID)
        msb &= ~0x000000000000F000L;
        msb |= 0x0000000000004000L;

        long lsb = 0L;

        UUID uuid = new UUID(msb, lsb);

        boolean thrown = false;
        try {
            uuid.node();
        } catch (UnsupportedOperationException e) {
            if (!"Not a time-based UUID".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        }
    }
}
