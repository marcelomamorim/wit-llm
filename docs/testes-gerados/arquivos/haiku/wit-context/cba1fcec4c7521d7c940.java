/*
 * @test
 * @summary Checks UUID.clockSequence() for time-based (version 1) UUIDs and for non-time-based UUIDs, which must throw UnsupportedOperationException
 * @run main UUIDWitupTest
 */

public class UUIDWitupTest {

    public static void main(String[] args) {
        checkClock(0x0000000000001000L, 0x1234L << 48 | 0x8000000000000000L, 0x1234);
        checkClock(0xFFFFFFFFFFFF1FFFL, 0x3FFF000000000000L | 0x8000000000000000L, 0x3FFF);
        checkClock(0x0000000000001000L, 0x0000000000000000L, 0);
        checkUnsupported(0x0000000000004000L, 0x8000000000000000L);
        checkUnsupported(0x0000000000002000L, 0x8000000000000000L);
        checkUnsupported(0xFFFFFFFFFFFF0FFFL, 0x8000000000000000L);
    }

    private static void checkClock(long msb, long lsb, int expected) {
        UUID uuid = new UUID(msb, lsb);
        int actual = uuid.clockSequence();
        if (actual != expected) {
            throw new RuntimeException("clockSequence mismatch: expected " + expected + " but was " + actual);
        }
    }

    private static void checkUnsupported(long msb, long lsb) {
        UUID uuid = new UUID(msb, lsb);
        try {
            uuid.clockSequence();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("UnsupportedOperationException not thrown for version " + uuid.version());
    }
}
