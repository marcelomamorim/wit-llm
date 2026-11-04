/*
 * @test
 * @summary Observable behavior of Period.addTo on ISO-based java.time temporals
 * @run main PeriodWitupTest
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class PeriodWitupTest {

    public static void main(String[] args) {
        check(LocalDate.of(2021, 4, 3),
              Period.of(1, 2, 3).addTo(LocalDate.of(2020, 1, 31)),
              "years+months+days with end-of-month clamping");

        check(LocalDate.of(2021, 2, 28),
              Period.of(1, 0, 0).addTo(LocalDate.of(2020, 2, 29)),
              "one year from leap day clamps to Feb 28");

        check(LocalDate.of(2022, 1, 1),
              Period.of(2, 0, 0).addTo(LocalDate.of(2020, 1, 1)),
              "years only path");

        check(LocalDate.of(2021, 3, 1),
              Period.of(0, 14, 0).addTo(LocalDate.of(2020, 1, 1)),
              "months only path, totalMonths 14");

        check(LocalDate.of(2020, 2, 25),
              Period.of(0, 0, -5).addTo(LocalDate.of(2020, 3, 1)),
              "negative days only path");

        LocalDate start = LocalDate.of(2020, 6, 15);
        check(start,
              Period.of(0, 0, 0).addTo(start),
              "zero period returns equal date");

        check(LocalDateTime.of(2020, 2, 15, 10, 30),
              Period.of(0, 1, 0).addTo(LocalDateTime.of(2020, 1, 15, 10, 30)),
              "time-of-day preserved for LocalDateTime");

        Period p = Period.of(1, 2, 3);
        check(LocalDate.of(2021, 4, 3),
              Period.from(p).addTo(LocalDate.of(2020, 1, 31)),
              "Period.from(TemporalAmount) yields same addition behavior");
    }

    static void check(Object expected, Object actual, String msg) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(msg + ": expected " + expected + " but got " + actual);
        }
    }
}
