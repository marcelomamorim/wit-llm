/*
 * @test
 * @summary Witup-derived tests for LocalTime.ofInstant
 * @run main LocalTimeWitupTest
 */

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testNullInstant();
        testNullZone();
        testEpochUtc();
        testPositiveOffset();
        testNegativeOffsetWrap();
        testNanos();
        testEndOfDay();
    }

    static void check(Object expected, Object actual, String msg) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(msg + ": expected " + expected + " but got " + actual);
        }
    }

    static void testNullInstant() {
        try {
            LocalTime.ofInstant(null, ZoneOffset.UTC);
            throw new RuntimeException("NullPointerException expected for null instant");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNullZone() {
        try {
            LocalTime.ofInstant(Instant.EPOCH, null);
            throw new RuntimeException("NullPointerException expected for null zone");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testEpochUtc() {
        check(LocalTime.MIDNIGHT, LocalTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC), "epoch UTC offset");
        check(LocalTime.MIDNIGHT, LocalTime.ofInstant(Instant.EPOCH, ZoneId.of("UTC")), "epoch ZoneId UTC");
        check(LocalTime.of(1, 0), LocalTime.ofInstant(Instant.ofEpochSecond(3600), ZoneOffset.UTC), "one hour UTC");
    }

    static void testPositiveOffset() {
        check(LocalTime.of(3, 0), LocalTime.ofInstant(Instant.ofEpochSecond(3600), ZoneOffset.ofHours(2)), "positive offset");
    }

    static void testNegativeOffsetWrap() {
        check(LocalTime.of(23, 0), LocalTime.ofInstant(Instant.EPOCH, ZoneOffset.ofHours(-1)), "negative offset wrap");
    }

    static void testNanos() {
        check(LocalTime.of(0, 0, 0, 123), LocalTime.ofInstant(Instant.ofEpochSecond(0, 123), ZoneOffset.UTC), "nanosecond preservation");
    }

    static void testEndOfDay() {
        check(LocalTime.of(23, 59, 59), LocalTime.ofInstant(Instant.ofEpochSecond(86399), ZoneOffset.UTC), "end of day");
    }
}
