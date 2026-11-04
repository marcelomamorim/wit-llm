/*
 * @test
 * @summary Witup tests for LocalDateTime.ofInstant null guards and basic conversion
 * @run main LocalDateTimeWitupTest
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testNullInstant();
        testNullZone();
        testEpochUtc();
        testFixedOffsetAndNanos();
    }

    static void testNullInstant() {
        try {
            LocalDateTime.ofInstant(null, ZoneOffset.UTC);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null instant");
    }

    static void testNullZone() {
        try {
            LocalDateTime.ofInstant(Instant.EPOCH, null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null zone");
    }

    static void testEpochUtc() {
        LocalDateTime actual = LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC);
        LocalDateTime expected = LocalDateTime.of(1970, 1, 1, 0, 0);
        if (!expected.equals(actual)) {
            throw new RuntimeException("unexpected result for epoch in UTC: " + actual);
        }
    }

    static void testFixedOffsetAndNanos() {
        Instant instant = Instant.ofEpochSecond(0L, 123L);
        LocalDateTime actual = LocalDateTime.ofInstant(instant, ZoneOffset.ofHours(2));
        LocalDateTime expected = LocalDateTime.of(1970, 1, 1, 2, 0, 0, 123);
        if (!expected.equals(actual)) {
            throw new RuntimeException("unexpected result for +02:00 with nanos: " + actual);
        }
    }
}
