/*
 * @test
 * @summary Test OffsetTime.of(LocalTime, ZoneOffset) factory method
 * @run main OffsetTimeWitupTest
 */

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {
    public static void main(String[] args) {
        testOfWithValidInputs();
        testOfWithNullTime();
        testOfWithNullOffset();
    }

    private static void testOfWithValidInputs() {
        LocalTime time = LocalTime.of(10, 15, 30, 123456789);
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(2, 30);
        OffsetTime offsetTime = OffsetTime.of(time, offset);
        if (!offsetTime.toLocalTime().equals(time)) {
            throw new AssertionError("OffsetTime.toLocalTime() does not match input time");
        }
        if (!offsetTime.getOffset().equals(offset)) {
            throw new AssertionError("OffsetTime.getOffset() does not match input offset");
        }
    }

    private static void testOfWithNullTime() {
        ZoneOffset offset = ZoneOffset.UTC;
        try {
            OffsetTime.of(null, offset);
            throw new AssertionError("Expected NullPointerException for null time");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testOfWithNullOffset() {
        LocalTime time = LocalTime.NOON;
        try {
            OffsetTime.of(time, null);
            throw new AssertionError("Expected NullPointerException for null offset");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
