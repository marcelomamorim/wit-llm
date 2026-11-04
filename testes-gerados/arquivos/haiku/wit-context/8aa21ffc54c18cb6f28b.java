/*
 * @test
 * @summary Checks UUID.node() returns the low 48 bits for time-based UUIDs and throws UnsupportedOperationException otherwise
 * @run main UUIDWitupTest
 */

import java.util.UUID;

public class UUIDWitupTest {

    public static void main(String[] args) {
        checkTimeBased(0x123456789ABC1DEFL, 0x8123456789ABCDEFL, 0x456789ABCDEFL);
        checkTimeBased(0x0000000000001000L, 0xFFFFFFFFFFFFFFFFL, 0x0000FFFFFFFFFFFFL);
        checkTimeBased(0x0000000000001000L, 0x0000000000000000L, 0x0L);

        checkUnsupported(0x0000000000000000L, 0x8123456789ABCDEFL);
        checkUnsupported(0x0000000000002000L, 0x8123456789ABCDEFL);
        checkUnsupported(0x0000000000003000L, 0x8123456789ABCDEFL);
        checkUnsupported(0x0000000000004000L, 0x8123456789ABCDEFL);
        checkUnsupported(0x0000000000005000L, 0x8123456789ABCDEFL);
        checkUnsupported(0x123456789ABC0DEFL, 0x8123456789ABCDEFL);
        checkUnsupported(0x123456789ABC2DEFL, 0x8123456789ABCDEFL);
    }

    private static void checkTimeBased(long msb, long lsb, long expectedNode) {
        UUID uuid = new UUID(msb, lsb);
        if (uuid.version() != 1) {
            throw new RuntimeException("precondition failed: version=" + uuid.version());
        }
        long actual = uuid.node();
        if (actual != expectedNode) {
            throw new RuntimeException("node() expected " + Long.toHexString(expectedNode)
                    + " but was " + Long.toHexString(actual));
        }
    }

    private static void checkUnsupported(long msb, long lsb) {
        UUID uuid = new UUID(msb, lsb);
        int version = uuid.version();
        try {
            uuid.node();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException for version " + version);
    }
}
