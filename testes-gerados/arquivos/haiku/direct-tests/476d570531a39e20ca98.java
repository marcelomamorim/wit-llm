/*
 * @test
 * @summary Tests observable behavior of ZonedDateTime.withZoneSameInstant
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testNullZoneThrowsNPE();
        testSameZoneReturnsSameInstance();
        testConvertsToNewZonePreservingInstant();
        testNanosArePreserved();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullZoneThrowsNPE() {
        ZonedDateTime z = ZonedDateTime.of(2020, 1, 15, 10, 30, 0, 0, ZoneOffset.UTC);
        try {
            z.withZoneSameInstant(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testSameZoneReturnsSameInstance() {
        ZoneId paris = ZoneId.of("Europe/Paris");
        ZonedDateTime z = ZonedDateTime.of(2020, 1, 15, 10, 30, 0, 0, paris);
        ZonedDateTime result = z.withZoneSameInstant(ZoneId.of("Europe/Paris"));
        check(result == z, "same zone should return this");
    }

    static void testConvertsToNewZonePreservingInstant() {
        ZonedDateTime utc = ZonedDateTime.of(2020, 1, 15, 10, 30, 0, 0, ZoneOffset.UTC);
        ZoneId ny = ZoneId.of("America/New_York");
        ZonedDateTime result = utc.withZoneSameInstant(ny);
        check(result.getZone().equals(ny), "zone should be the requested zone");
        check(result.toInstant().equals(utc.toInstant()), "instant should be preserved");
        check(result.getHour() == 5, "expected local hour 5 in New York");
        check(result.getMinute() == 30, "expected local minute 30 in New York");
        check(result.getOffset().equals(ZoneOffset.of("-05:00")), "expected offset -05:00");
    }

    static void testNanosArePreserved() {
        ZonedDateTime utc = ZonedDateTime.of(2020, 6, 1, 12, 0, 0, 123456789, ZoneOffset.UTC);
        ZonedDateTime result = utc.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
        check(result.getNano() == 123456789, "nanos should be preserved");
        check(result.toEpochSecond() == utc.toEpochSecond(), "epoch seconds should be preserved");
    }
}
