/*
 * @test
 * @summary Test OffsetTime.of(LocalTime, ZoneOffset) for null argument handling and normal creation
 * @run main OffsetTimeWitupTest
 */

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {
    public static void main(String[] args) {
        testNullTime();
        testNullOffset();
        testValidCreation();
    }

    private static void testNullTime() {
        try {
            OffsetTime.of(null, ZoneOffset.UTC);
            throw new AssertionError("Expected NullPointerException for null time");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullOffset() {
        try {
            OffsetTime.of(LocalTime.NOON, null);
            throw new AssertionError("Expected NullPointerException for null offset");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testValidCreation() {
        LocalTime time = LocalTime.of(10, 15, 30, 123456789);
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(2, 30);
        OffsetTime offsetTime = OffsetTime.of(time, offset);
        if (!offsetTime.toLocalTime().equals(time)) {
            throw new AssertionError("LocalTime mismatch");
        }
        if (!offsetTime.getOffset().equals(offset)) {
            throw new AssertionError("ZoneOffset mismatch");
        }
    }
}
