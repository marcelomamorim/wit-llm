/*
 * @test
 * @summary Test OffsetTime.ofInstant(Instant, ZoneId) basic and boundary behaviors
 * @run main OffsetTimeWitupTest
 */

import java.time.Instant;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneRules;
import java.util.Objects;

public class OffsetTimeWitupTest {
    public static void main(String[] args) throws Exception {
        testBasicConversion();
        testMidnightInstant();
        testOffsetPositive();
        testOffsetNegative();
        testNullInstant();
        testNullZone();
    }

    private static void testBasicConversion() {
        Instant instant = Instant.ofEpochSecond(0L, 0);
        ZoneId zone = ZoneOffset.UTC;
        OffsetTime ot = OffsetTime.ofInstant(instant, zone);
        if (!ot.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            throw new AssertionError("Expected midnight local time");
        }
        if (!ot.getOffset().equals(ZoneOffset.UTC)) {
            throw new AssertionError("Expected UTC offset");
        }
    }

    private static void testMidnightInstant() {
        Instant instant = Instant.ofEpochSecond(SECONDS_PER_DAY, 0); // next day midnight
        ZoneId zone = ZoneOffset.UTC;
        OffsetTime ot = OffsetTime.ofInstant(instant, zone);
        if (!ot.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            throw new AssertionError("Expected midnight local time for next day instant");
        }
    }

    private static void testOffsetPositive() {
        Instant instant = Instant.ofEpochSecond(0L, 0);
        ZoneOffset offset = ZoneOffset.ofHours(2);
        OffsetTime ot = OffsetTime.ofInstant(instant, offset);
        LocalTime expectedTime = LocalTime.of(2, 0);
        if (!ot.toLocalTime().equals(expectedTime)) {
            throw new AssertionError("Expected local time 02:00 for +02:00 offset");
        }
        if (!ot.getOffset().equals(offset)) {
            throw new AssertionError("Expected offset +02:00");
        }
    }

    private static void testOffsetNegative() {
        Instant instant = Instant.ofEpochSecond(0L, 0);
        ZoneOffset offset = ZoneOffset.ofHours(-3);
        OffsetTime ot = OffsetTime.ofInstant(instant, offset);
        LocalTime expectedTime = LocalTime.of(21, 0); // previous day 21:00
        if (!ot.toLocalTime().equals(expectedTime)) {
            throw new AssertionError("Expected local time 21:00 for -03:00 offset");
        }
        if (!ot.getOffset().equals(offset)) {
            throw new AssertionError("Expected offset -03:00");
        }
    }

    private static void testNullInstant() {
        try {
            OffsetTime.ofInstant(null, ZoneOffset.UTC);
            throw new AssertionError("Expected NullPointerException for null instant");
        } catch (NullPointerException ex) {
            // expected
        }
    }

    private static void testNullZone() {
        try {
            OffsetTime.ofInstant(Instant.EPOCH, null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException ex) {
            // expected
        }
    }

    private static final int SECONDS_PER_DAY = 24 * 60 * 60;
}
