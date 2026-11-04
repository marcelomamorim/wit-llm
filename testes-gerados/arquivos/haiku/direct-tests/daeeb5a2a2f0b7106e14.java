/*
 * @test
 * @summary Verifies YearMonth.now(Clock) derives the year and month from the clock's local date
 * @run main YearMonthWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        Clock midYear = Clock.fixed(Instant.parse("2018-06-15T10:00:00Z"), ZoneOffset.UTC);
        check(YearMonth.of(2018, 6).equals(YearMonth.now(midYear)), "mid-year clock");

        Clock yearEnd = Clock.fixed(Instant.parse("2000-12-31T23:30:00Z"), ZoneOffset.UTC);
        check(YearMonth.of(2000, 12).equals(YearMonth.now(yearEnd)), "last instant of year");

        Clock positiveOffset = Clock.fixed(Instant.parse("2018-01-31T23:30:00Z"), ZoneOffset.ofHours(2));
        check(YearMonth.of(2018, 2).equals(YearMonth.now(positiveOffset)), "zone offset moves date to next month");

        Clock negativeOffset = Clock.fixed(Instant.parse("2018-03-01T00:30:00Z"), ZoneOffset.ofHours(-5));
        check(YearMonth.of(2018, 2).equals(YearMonth.now(negativeOffset)), "negative zone offset moves date to previous month");

        boolean npe = false;
        try {
            YearMonth.now(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null clock must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
