/*
 * @test
 * @summary Tests UUID.timestamp() for version 1 UUIDs and rejects non-time-based UUIDs
 * @run main UUIDWitupTest
 */

import java.util.UUID;

public class UUIDWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkTimestamp(long mostSigBits, long leastSigBits, long expected) {
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        check(uuid.version() == 1, "expected version 1 for " + uuid);
        long actual = uuid.timestamp();
        check(actual == expected,
              "timestamp mismatch for " + uuid + ": expected " + Long.toHexString(expected)
              + " but was " + Long.toHexString(actual));
    }

    static void testMinimalTimestamp() {
        // time_low=1, time_mid=0, time_hi=0, version=1
        checkTimestamp(0x0000000100001000L, 0x8000000000000000L, 1L);
    }

    static void testMaximalTimestamp() {
        // time_low=0xFFFFFFFF, time_mid=0xFFFF, time_hi=0xFFF, version=1
        checkTimestamp(0xFFFFFFFFFFFF1FFFL, 0x8000000000000000L, 0x0FFFFFFFFFFFFFFFL);
    }

    static void testMixedFieldsTimestamp() {
        // time_low=0x12345678, time_mid=0x9ABC, time_hi=0xDEF, version=1
        checkTimestamp(0x123456789ABC1DEFL, 0x8000000000000000L, 0x0DEF9ABC12345678L);
    }

    static void testNonTimeBasedThrows() {
        // version 4 (random-based)
        UUID uuid = new UUID(0x0000000000004000L, 0x8000000000000000L);
        check(uuid.version() == 4, "expected version 4");
        boolean thrown = false;
        try {
            uuid.timestamp();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "expected UnsupportedOperationException for version 4");
    }

    public static void main(String[] args) {
        testMinimalTimestamp();
        testMaximalTimestamp();
        testMixedFieldsTimestamp();
        testNonTimeBasedThrows();
    }
}
