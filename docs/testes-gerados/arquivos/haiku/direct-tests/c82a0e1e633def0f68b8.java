/*
 * @test
 * @summary Verify LocalDateTime.minusMinutes subtracts minutes with correct date rollover and preserves seconds and nanoseconds
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        // Subtraction within the same day
        check(LocalDateTime.of(2020, 1, 1, 12, 30).minusMinutes(15), "2020-01-01T12:15");

        // Zero minutes returns an equal value
        LocalDateTime base = LocalDateTime.of(2020, 1, 1, 12, 30);
        check(base.minusMinutes(0), "2020-01-01T12:30");

        // Crossing midnight into the previous year
        check(LocalDateTime.of(2020, 1, 1, 0, 0).minusMinutes(1), "2019-12-31T23:59");

        // Crossing a leap-day boundary
        check(LocalDateTime.of(2020, 3, 1, 0, 0).minusMinutes(1), "2020-02-29T23:59");

        // Exactly one day in minutes gives the previous day at the same time
        check(LocalDateTime.of(2020, 5, 10, 8, 45).minusMinutes(1440), "2020-05-09T08:45");

        // Seconds and nanoseconds are preserved
        check(LocalDateTime.of(2020, 1, 1, 0, 5, 7, 123).minusMinutes(5), "2020-01-01T00:00:07.000000123");

        // Consistency with plusMinutes of the negated amount
        LocalDateTime x = LocalDateTime.of(2021, 6, 15, 10, 20, 30);
        if (!x.minusMinutes(1000).equals(x.plusMinutes(-1000))) {
            throw new RuntimeException("minusMinutes(1000) differs from plusMinutes(-1000)");
        }

        // Result is a new value; the original is unchanged
        LocalDateTime original = LocalDateTime.of(2020, 1, 1, 1, 0);
        LocalDateTime result = original.minusMinutes(30);
        check(original, "2020-01-01T01:00");
        check(result, "2020-01-01T00:30");
    }

    static void check(LocalDateTime actual, String expected) {
        if (!actual.toString().equals(expected)) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }
}
