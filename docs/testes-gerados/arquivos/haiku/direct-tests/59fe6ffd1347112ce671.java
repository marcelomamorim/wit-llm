/*
 * @test
 * @summary Verify OffsetTime.ofInstant computes local time-of-day and offset from an Instant and ZoneId, and rejects null arguments
 * @run main OffsetTimeWitupTest
 */

import java.time.Instant;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        epochAtUtc();
        negativeEpochWrapsToPreviousDay();
        positiveOffsetShiftsLocalTime();
        nanosArePreserved();
        fixedOffsetAcrossMidnight();
        namedZoneWinterOffset();
        nullInstantThrowsNpe();
        nullZoneThrowsNpe();
    }

    static void epochAtUtc() {
        OffsetTime t = OffsetTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC);
        check(t.getHour() == 0 && t.getMinute() == 0 && t.getSecond() == 0 && t.getNano() == 0,
              "epoch at UTC should be 00:00:00");
        check(t.getOffset().equals(ZoneOffset.UTC), "offset should be UTC");
    }

    static void negativeEpochWrapsToPreviousDay() {
        OffsetTime t = OffsetTime.ofInstant(Instant.ofEpochSecond(-1), ZoneOffset.UTC);
        check(t.getHour() == 23 && t.getMinute() == 59 && t.getSecond() == 59,
              "one second before epoch should be 23:59:59");
    }

    static void positiveOffsetShiftsLocalTime() {
        OffsetTime t = OffsetTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.ofHours(2));
        check(t.getHour() == 2 && t.getMinute() == 0, "epoch with +02:00 should be 02:00");
        check(t.getOffset().equals(ZoneOffset.ofHours(2)), "offset should be +02:00");
    }

    static void nanosArePreserved() {
        OffsetTime t = OffsetTime.ofInstant(Instant.ofEpochSecond(0, 123), ZoneOffset.UTC);
        check(t.getNano() == 123, "nano-of-second should be preserved");
    }

    static void fixedOffsetAcrossMidnight() {
        OffsetTime t = OffsetTime.ofInstant(Instant.parse("2018-01-01T23:30:00Z"), ZoneOffset.ofHours(2));
        check(t.getHour() == 1 && t.getMinute() == 30,
              "23:30Z with +02:00 should be 01:30 local time");
        check(t.getOffset().equals(ZoneOffset.ofHours(2)), "offset should be +02:00");
    }

    static void namedZoneWinterOffset() {
        ZoneId zone = ZoneOffset.ofHours(-5);
        OffsetTime t = OffsetTime.ofInstant(Instant.parse("2018-01-01T12:00:00Z"), zone);
        check(t.getHour() == 7 && t.getMinute() == 0, "12:00Z with -05:00 should be 07:00");
        check(t.getOffset().equals(ZoneOffset.ofHours(-5)), "offset should be -05:00");
    }

    static void nullInstantThrowsNpe() {
        try {
            OffsetTime.ofInstant(null, ZoneOffset.UTC);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("null instant should throw NullPointerException");
    }

    static void nullZoneThrowsNpe() {
        try {
            OffsetTime.ofInstant(Instant.ofEpochSecond(0), null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("null zone should throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
