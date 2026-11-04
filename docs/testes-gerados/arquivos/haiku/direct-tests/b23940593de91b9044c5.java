/*
 * @test
 * @summary Verifies Year.now(Clock) derives the year from the clock's instant and zone
 * @run main YearWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.Year;
import java.time.ZoneOffset;

public class YearWitupTest {

    public static void main(String[] args) {
        Clock utc = Clock.fixed(Instant.parse("2020-06-15T12:00:00Z"), ZoneOffset.UTC);
        check(Year.now(utc).getValue() == 2020, "expected 2020 for UTC clock");
        check(Year.now(utc).equals(Year.of(2020)), "expected Year.of(2020) equality");

        Clock plus2 = Clock.fixed(Instant.parse("1999-12-31T23:30:00Z"), ZoneOffset.ofHours(2));
        check(Year.now(plus2).getValue() == 2000, "zone +02:00 should roll over to 2000");

        Clock minus5 = Clock.fixed(Instant.parse("2000-01-01T02:00:00Z"), ZoneOffset.ofHours(-5));
        check(Year.now(minus5).getValue() == 1999, "zone -05:00 should stay in 1999");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
