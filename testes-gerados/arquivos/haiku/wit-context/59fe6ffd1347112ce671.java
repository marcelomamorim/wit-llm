/*
 * @test
 * @summary Witup-generated tests for OffsetTime.ofInstant null guards and observable conversion
 * @run main OffsetTimeWitupTest
 */

import java.time.Instant;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testNullInstant();
        testNullZone();
        testEpochAtUtc();
        testPositiveOffset();
        testNegativeOffsetWrapsToPreviousDay();
    }

    static void testNullInstant() {
        expectNpe(null, ZoneOffset.UTC, "null instant");
    }

    static void testNullZone() {
        expectNpe(Instant.ofEpochSecond(0), null, "null zone");
    }

    static void testEpochAtUtc() {
        OffsetTime actual = OffsetTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC);
        check(OffsetTime.of(LocalTime.MIDNIGHT, ZoneOffset.UTC), actual, "epoch at UTC");
    }

    static void testPositiveOffset() {
        OffsetTime actual = OffsetTime.ofInstant(Instant.ofEpochSecond(3600, 5), ZoneOffset.ofHours(1));
        check(OffsetTime.of(LocalTime.of(2, 0, 0, 5), ZoneOffset.ofHours(1)), actual, "positive offset");
    }

    static void testNegativeOffsetWrapsToPreviousDay() {
        OffsetTime actual = OffsetTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.ofHours(-1));
        check(OffsetTime.of(23, 0, 0, 0, ZoneOffset.ofHours(-1)), actual, "negative offset wrap");
    }

    static void expectNpe(Instant instant, ZoneId zone, String name) {
        try {
            OffsetTime.ofInstant(instant, zone);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(name + ": expected NullPointerException");
    }

    static void check(Object expected, Object actual, String name) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(name + ": expected " + expected + " but got " + actual);
        }
    }
}
