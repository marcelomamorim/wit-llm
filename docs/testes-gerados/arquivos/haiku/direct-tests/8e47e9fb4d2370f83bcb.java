/*
 * @test
 * @summary Verifies OffsetTime.format(DateTimeFormatter) output and null formatter handling
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class OffsetTimeWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void checkEquals(String expected, String actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new RuntimeException(message + ": expected <" + expected + "> but was <" + actual + ">");
        }
    }

    static void testNullFormatterThrowsNpe() {
        OffsetTime t = OffsetTime.of(10, 15, 30, 0, ZoneOffset.ofHours(2));
        boolean thrown = false;
        try {
            t.format(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "format(null) must throw NullPointerException");
    }

    static void testIsoLocalTime() {
        OffsetTime t = OffsetTime.of(10, 15, 30, 0, ZoneOffset.ofHours(2));
        checkEquals("10:15:30", t.format(DateTimeFormatter.ISO_LOCAL_TIME), "ISO_LOCAL_TIME");
    }

    static void testIsoOffsetTimeWithPositiveOffset() {
        OffsetTime t = OffsetTime.of(10, 15, 30, 0, ZoneOffset.ofHours(2));
        checkEquals("10:15:30+02:00", t.format(DateTimeFormatter.ISO_OFFSET_TIME), "ISO_OFFSET_TIME +02:00");
    }

    static void testIsoOffsetTimeWithUtcUsesZ() {
        OffsetTime t = OffsetTime.of(0, 0, 0, 0, ZoneOffset.UTC);
        checkEquals("00:00:00Z", t.format(DateTimeFormatter.ISO_OFFSET_TIME), "ISO_OFFSET_TIME UTC");
    }

    static void testCustomPatternWithNegativeOffset() {
        OffsetTime t = OffsetTime.of(1, 2, 3, 450000000, ZoneOffset.ofHours(-5));
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss.SSS xxx");
        checkEquals("01:02:03.450 -05:00", t.format(f), "custom pattern with offset");
    }

    static void testFormatDoesNotChangeValue() {
        OffsetTime t = OffsetTime.of(23, 59, 59, 0, ZoneOffset.ofHours(-3));
        String first = t.format(DateTimeFormatter.ISO_OFFSET_TIME);
        String second = t.format(DateTimeFormatter.ISO_OFFSET_TIME);
        checkEquals(first, second, "repeated format must be deterministic");
        checkEquals("23:59:59-03:00", second, "format after repeated call");
    }

    public static void main(String[] args) {
        testNullFormatterThrowsNpe();
        testIsoLocalTime();
        testIsoOffsetTimeWithPositiveOffset();
        testIsoOffsetTimeWithUtcUsesZ();
        testCustomPatternWithNegativeOffset();
        testFormatDoesNotChangeValue();
    }
}
