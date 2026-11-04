/*
 * @test
 * @summary Verify UUID.timestamp() returns the time value for version 1 UUIDs and throws UnsupportedOperationException for other versions
 * @run main UUIDWitupTest
 */

public class UUIDWitupTest {

    public static void main(String[] args) {
        testVersion1Timestamp();
        testNonVersion1Throws(0x550e8400e29b41d4L, 0xa716446655440000L);
        testNonVersion1Throws(0x3d1b4a9c8e7f3a10L, 0x8000000000000001L);
    }

    private static void testVersion1Timestamp() {
        UUID uuid = new UUID(0xf81d4fae7dec11d0L, 0xa76500a0c91e6bf6L);
        if (uuid.version() != 1) {
            throw new RuntimeException("Expected version 1, got " + uuid.version());
        }
        long expected = 0x01d07decf81d4faeL;
        long actual = uuid.timestamp();
        if (actual != expected) {
            throw new RuntimeException("Unexpected timestamp: " + Long.toHexString(actual)
                    + " expected: " + Long.toHexString(expected));
        }
    }

    private static void testNonVersion1Throws(long msb, long lsb) {
        UUID uuid = new UUID(msb, lsb);
        int version = uuid.version();
        if (version == 1) {
            throw new RuntimeException("Test UUID unexpectedly has version 1");
        }
        try {
            uuid.timestamp();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException for version " + version);
    }
}
