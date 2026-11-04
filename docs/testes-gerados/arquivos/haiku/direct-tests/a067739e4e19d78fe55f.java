/*
 * @test
 * @summary MonthDay.now(Clock) derives month and day from the clock's date in its zone
 * @run main MonthDayWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.ZoneOffset;

public class MonthDayWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Clock utcClock = Clock.fixed(Instant.parse("2021-07-04T10:15:30Z"), ZoneOffset.UTC);
        MonthDay md = MonthDay.now(utcClock);
        check(md.equals(MonthDay.of(7, 4)), "expected 07-04 but got " + md);

        Clock leapClock = Clock.fixed(Instant.parse("2020-02-29T12:00:00Z"), ZoneOffset.UTC);
        check(MonthDay.now(leapClock).equals(MonthDay.of(2, 29)), "leap day not preserved");

        Clock plus2Clock = Clock.fixed(Instant.parse("2021-07-04T23:30:00Z"), ZoneOffset.ofHours(2));
        check(MonthDay.now(plus2Clock).equals(MonthDay.of(7, 5)),
              "clock zone not applied: expected 07-05");

        Clock minus3Clock = Clock.fixed(Instant.parse("2021-01-01T01:00:00Z"), ZoneOffset.ofHours(-3));
        check(MonthDay.now(minus3Clock).equals(MonthDay.of(12, 31)),
              "clock zone not applied: expected 12-31");

        LocalDate date = LocalDate.now(utcClock);
        check(MonthDay.now(utcClock).equals(MonthDay.from(date)),
              "now(clock) should match MonthDay.from(LocalDate.now(clock))");

        try {
            MonthDay.now(null);
            throw new RuntimeException("expected NullPointerException for null clock");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
