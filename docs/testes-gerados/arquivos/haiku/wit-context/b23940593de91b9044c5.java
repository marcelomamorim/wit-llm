/*
 * @test
 * @summary Witup-derived tests for Year.now(Clock)
 * @run main YearWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.Year;
import java.time.ZoneOffset;

public class YearWitupTest {
    public static void main(String[] args) {
        Clock utc = Clock.fixed(Instant.parse("2020-06-15T10:00:00Z"), ZoneOffset.UTC);
        check(Year.now(utc).getValue() == 2020, "utc mid-year");

        Clock plus2 = Clock.fixed(Instant.parse("2019-12-31T23:30:00Z"), ZoneOffset.ofHours(2));
        check(Year.now(plus2).getValue() == 2020, "zone ahead crosses new year");

        Clock minus5 = Clock.fixed(Instant.parse("2021-01-01T02:00:00Z"), ZoneOffset.ofHours(-5));
        check(Year.now(minus5).getValue() == 2020, "zone behind keeps previous year");

        boolean npe = false;
        try {
            Year.now((Clock) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null clock must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
