/*
 * @test
 * @summary Test java.util.UUID timestamp() method behavior
 * @run main java.util.UUIDWitupTest
 */

public class UUIDWitupTest {
    public static void main(String[] args) {
        testTimestampWithVersion1();
        testTimestampWithNonVersion1();
    }

    private static void testTimestampWithVersion1() {
        // Construct a UUID with version 1 (time-based)
        // version is bits 12-15 of mostSigBits, so set version to 1
        long msb = 0L;
        // Set version to 1: clear bits 12-15 and set to 1
        msb &= ~0x000000000000F000L; // clear version bits
        msb |= 0x0000000000001000L;  // set version to 1

        // Set some bits to test timestamp extraction
        // Use a known pattern for mostSigBits
        msb |= 0x00000ABCDEF00000L; // arbitrary bits in timestamp fields

        long lsb = 0L;

        java.util.UUID uuid = new java.util.UUID(msb, lsb);

        // The timestamp method should return a long composed from parts of mostSigBits
        long expected = ((msb & 0x0FFFL) << 48) | (((msb >> 16) & 0x0FFFFL) << 32) | ((msb >>> 32) & 0xFFFFFFFFL);

        long actual = uuid.timestamp();

        if (actual != expected) {
            throw new AssertionError("timestamp() returned " + actual + ", expected " + expected);
        }
    }

    private static void testTimestampWithNonVersion1() {
        // Construct a UUID with version != 1
        long msb = 0L;
        // Set version to 4 (random-based UUID)
        msb &= ~0x000000000000F000L; // clear version bits
        msb |= 0x0000000000004000L;  // set version to 4

        long lsb = 0L;

        java.util.UUID uuid = new java.util.UUID(msb, lsb);

        try {
            uuid.timestamp();
            throw new AssertionError("Expected UnsupportedOperationException for non-version 1 UUID");
        } catch (UnsupportedOperationException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Not a time-based UUID")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
