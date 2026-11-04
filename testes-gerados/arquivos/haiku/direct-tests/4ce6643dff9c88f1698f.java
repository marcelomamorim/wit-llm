/*
 * @test
 * @summary Verifies IsoChronology.zonedDateTime(Instant, ZoneId) delegates to ZonedDateTime.ofInstant
 * @run main IsoChronologyWitupTest
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        testEpochUtc();
        testEpochWithPositiveOffset();
        testMatchesZonedDateTimeOfInstant();
        testPreservesInstant();
        testChronologyIsIso();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEpochUtc() {
        ZonedDateTime zdt = IsoChronology.INSTANCE.zonedDateTime(Instant.EPOCH, ZoneOffset.UTC);
        check(zdt.getYear() == 1970, "year expected 1970 but was " + zdt.getYear());
        check(zdt.getMonthValue() == 1, "month expected 1");
        check(zdt.getDayOfMonth() == 1, "day expected 1");
        check(zdt.getHour() == 0, "hour expected 0 but was " + zdt.getHour());
        check(zdt.getMinute() == 0, "minute expected 0");
        check(zdt.getOffset().equals(ZoneOffset.UTC), "offset expected UTC");
        check(zdt.getZone().equals(ZoneOffset.UTC), "zone expected UTC");
    }

    static void testEpochWithPositiveOffset() {
        ZoneId zone = ZoneOffset.ofHours(2);
        ZonedDateTime zdt = IsoChronology.INSTANCE.zonedDateTime(Instant.EPOCH, zone);
        check(zdt.getHour() == 2, "hour expected 2 but was " + zdt.getHour());
        check(zdt.getDayOfMonth() == 1, "day expected 1 at +02:00");
        check(zdt.getOffset().equals(ZoneOffset.ofHours(2)), "offset expected +02:00");
    }

    static void testMatchesZonedDateTimeOfInstant() {
        Instant instant = Instant.ofEpochSecond(1_500_000_000L, 123_000_000L);
        ZoneId zone = ZoneOffset.ofHoursMinutes(-3, -30);
        ZonedDateTime expected = ZonedDateTime.ofInstant(instant, zone);
        ZonedDateTime actual = IsoChronology.INSTANCE.zonedDateTime(instant, zone);
        check(expected.equals(actual), "result differs from ZonedDateTime.ofInstant: "
                + actual + " vs " + expected);
    }

    static void testPreservesInstant() {
        Instant instant = Instant.ofEpochSecond(-86_400L, 999_999_999L);
        ZonedDateTime zdt = IsoChronology.INSTANCE.zonedDateTime(instant, ZoneOffset.ofHours(5));
        check(zdt.toInstant().equals(instant), "instant not preserved");
        check(zdt.getNano() == 999_999_999, "nano not preserved");
    }

    static void testChronologyIsIso() {
        ZonedDateTime zdt = IsoChronology.INSTANCE.zonedDateTime(Instant.EPOCH, ZoneOffset.UTC);
        check(zdt.getChronology() == IsoChronology.INSTANCE, "chronology expected ISO");
    }
}
