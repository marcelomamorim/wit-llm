/*
 * @test
 * @summary Tests ZonedDateTime.parse(CharSequence) using the public ISO zoned date-time format
 * @run main ZonedDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class ZonedDateTimeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testParseWithZoneRegion() {
        ZonedDateTime zdt = ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]");
        check(zdt.getYear() == 2007, "year");
        check(zdt.getMonthValue() == 12, "month");
        check(zdt.getDayOfMonth() == 3, "day");
        check(zdt.getHour() == 10, "hour");
        check(zdt.getMinute() == 15, "minute");
        check(zdt.getSecond() == 30, "second");
        check(zdt.getNano() == 0, "nano");
        check(zdt.getZone().getId().equals("Europe/Paris"), "zone id");
        check(zdt.getOffset().getTotalSeconds() == 3600, "offset");
    }

    static void testParseWithOffsetOnly() {
        ZonedDateTime zdt = ZonedDateTime.parse("2007-12-03T10:15:30+01:00");
        check(zdt.getHour() == 10, "hour offset-only");
        check(zdt.getZone().equals(ZoneOffset.ofHours(1)), "zone should be offset");
        check(zdt.getOffset().equals(ZoneOffset.ofHours(1)), "offset value");
    }

    static void testParseWithNanos() {
        ZonedDateTime zdt = ZonedDateTime.parse("2007-12-03T10:15:30.123456789+01:00[Europe/Paris]");
        check(zdt.getNano() == 123456789, "nano parsed");
    }

    static void testRoundTripToString() {
        String text = "2007-12-03T10:15:30+01:00[Europe/Paris]";
        ZonedDateTime expected = ZonedDateTime.of(2007, 12, 3, 10, 15, 30, 0, ZoneId.of("Europe/Paris"));
        ZonedDateTime parsed = ZonedDateTime.parse(text);
        check(parsed.equals(expected), "parsed equals constructed");
        check(parsed.toString().equals(text), "toString round trip");
    }

    static void testParseCharSequenceNotString() {
        CharSequence cs = new StringBuilder("2007-12-03T10:15:30+01:00[Europe/Paris]");
        ZonedDateTime zdt = ZonedDateTime.parse(cs);
        check(zdt.getHour() == 10, "CharSequence input");
    }

    static void testParseInvalidThrows() {
        boolean thrown = false;
        try {
            ZonedDateTime.parse("2007-12-03");
        } catch (DateTimeParseException e) {
            thrown = true;
        }
        check(thrown, "missing time must throw DateTimeParseException");
    }

    static void testParseGarbageThrows() {
        boolean thrown = false;
        try {
            ZonedDateTime.parse("not-a-date");
        } catch (DateTimeParseException e) {
            thrown = true;
        }
        check(thrown, "garbage must throw DateTimeParseException");
    }

    static void testParseNullThrowsNpe() {
        boolean thrown = false;
        try {
            ZonedDateTime.parse(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null must throw NullPointerException");
    }

    public static void main(String[] args) {
        testParseWithZoneRegion();
        testParseWithOffsetOnly();
        testParseWithNanos();
        testRoundTripToString();
        testParseCharSequenceNotString();
        testParseInvalidThrows();
        testParseGarbageThrows();
        testParseNullThrowsNpe();
    }
}
