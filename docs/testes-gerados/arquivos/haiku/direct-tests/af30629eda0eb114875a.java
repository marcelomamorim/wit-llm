/*
 * @test
 * @summary Verifies that TemporalAdjusters.lastInMonth returns the last occurrence of the given weekday in the month
 * @run main TemporalAdjustersWitupTest
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class TemporalAdjustersWitupTest {

    public static void main(String[] args) {
        check(LocalDate.of(2024, 1, 15), DayOfWeek.WEDNESDAY, LocalDate.of(2024, 1, 31));
        check(LocalDate.of(2024, 1, 15), DayOfWeek.MONDAY, LocalDate.of(2024, 1, 29));
        check(LocalDate.of(2024, 2, 1), DayOfWeek.THURSDAY, LocalDate.of(2024, 2, 29));
        check(LocalDate.of(2024, 2, 10), DayOfWeek.FRIDAY, LocalDate.of(2024, 2, 23));
        check(LocalDate.of(2023, 2, 3), DayOfWeek.TUESDAY, LocalDate.of(2023, 2, 28));

        LocalDate expected = LocalDate.of(2024, 1, 31);
        LocalDate once = LocalDate.of(2024, 1, 5).with(TemporalAdjusters.lastInMonth(DayOfWeek.WEDNESDAY));
        LocalDate twice = once.with(TemporalAdjusters.lastInMonth(DayOfWeek.WEDNESDAY));
        if (!expected.equals(once) || !once.equals(twice)) {
            throw new RuntimeException("lastInMonth is not idempotent for the last weekday of the month");
        }
    }

    static void check(LocalDate input, DayOfWeek dayOfWeek, LocalDate expected) {
        LocalDate actual = input.with(TemporalAdjusters.lastInMonth(dayOfWeek));
        if (!expected.equals(actual)) {
            throw new RuntimeException("Unexpected result for " + dayOfWeek + " from " + input
                    + ": expected " + expected + " but got " + actual);
        }
    }
}
