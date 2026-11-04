/*
 * @test
 * @summary Witup-derived checks for OffsetDateTime.toInstant()
 * @run main OffsetDateTimeWitupTest
 */

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        OffsetDateTime positive = OffsetDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(0, 0), ZoneOffset.ofHours(2));
        check(positive.toInstant().equals(Instant.parse("2019-12-31T22:00:00Z")), "positive offset conversion");

        OffsetDateTime negative = OffsetDateTime.of(2000, 1, 1, 12, 0, 0, 123456789, ZoneOffset.ofHoursMinutes(-5, -30));
        check(negative.toInstant().equals(Instant.parse("2000-01-01T17:30:00.123456789Z")), "negative offset with nanos");

        OffsetDateTime utc = OffsetDateTime.of(LocalDateTime.of(2000, 1, 1, 12, 0), ZoneOffset.UTC);
        check(utc.toInstant().equals(Instant.parse("2000-01-01T12:00:00Z")), "UTC offset conversion");

        boolean npe = false;
        try {
            OffsetDateTime.of(LocalDateTime.of(2000, 1, 1, 12, 0), null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null offset must be rejected before toInstant can observe it");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
