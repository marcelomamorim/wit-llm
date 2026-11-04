/*
 * @test
 * @summary LocalDate.until(ChronoLocalDate) returns the expected Period for forward, backward, same-date and month-end cases
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        // same date gives zero period
        check(LocalDate.of(2020, 5, 10).until(LocalDate.of(2020, 5, 10)), 0, 0, 0);

        // forward, no day borrow
        check(LocalDate.of(2020, 1, 1).until(LocalDate.of(2021, 3, 15)), 1, 2, 14);

        // forward, day borrow from month arithmetic (Jan 31 -> Feb 1)
        check(LocalDate.of(2020, 1, 31).until(LocalDate.of(2020, 2, 1)), 0, 0, 1);

        // backward, no adjustment branch taken
        check(LocalDate.of(2020, 3, 15).until(LocalDate.of(2020, 1, 10)), 0, -2, -5);

        // backward with positive day difference: totalMonths++ and days -= lengthOfMonth
        check(LocalDate.of(2020, 3, 10).until(LocalDate.of(2020, 1, 20)), 0, -1, -21);

        // leap day anchor, forward with clamped day arithmetic
        check(LocalDate.of(2000, 2, 29).until(LocalDate.of(2004, 2, 28)), 3, 11, 30);

        // result via ChronoLocalDate parameter type
        ChronoLocalDate end = LocalDate.of(2021, 3, 15);
        check(LocalDate.of(2020, 1, 1).until(end), 1, 2, 14);

        // consistency: adding the computed period to the start yields the end date
        LocalDate start = LocalDate.of(2019, 11, 30);
        LocalDate target = LocalDate.of(2021, 2, 28);
        Period p = start.until(target);
        if (!start.plus(p).equals(target)) {
            throw new RuntimeException("start.plus(until) mismatch: " + p);
        }
    }

    static void check(Period p, int years, int months, int days) {
        if (p.getYears() != years || p.getMonths() != months || p.getDays() != days) {
            throw new RuntimeException("Unexpected period: " + p
                    + " expected Y=" + years + " M=" + months + " D=" + days);
        }
    }
}
