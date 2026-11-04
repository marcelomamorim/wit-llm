/*
 * @test
 * @summary Test java.time.chrono.MinguoDate.until(ChronoLocalDate) method
 * @run main MinguoDateWitupTest
 */

import java.time.chrono.MinguoDate;
import java.time.chrono.MinguoChronology;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.LocalDate;
import java.time.Period;

public class MinguoDateWitupTest {
    public static void main(String[] args) {
        testUntilSameDate();
        testUntilLaterDate();
        testUntilEarlierDate();
    }

    private static void testUntilSameDate() {
        MinguoDate date = MinguoDate.of(110, 5, 20); // corresponds to ISO 2021-05-20
        ChronoLocalDate endDate = date;
        ChronoPeriod period = date.until(endDate);
        // Period should be zero
        if (period.get(ChronoPeriod.YEARS) != 0 || period.get(ChronoPeriod.MONTHS) != 0 || period.get(ChronoPeriod.DAYS) != 0) {
            throw new AssertionError("Expected zero period when until same date");
        }
    }

    private static void testUntilLaterDate() {
        MinguoDate start = MinguoDate.of(110, 1, 1); // ISO 2021-01-01
        MinguoDate end = MinguoDate.of(111, 2, 3);   // ISO 2022-02-03
        ChronoPeriod period = start.until(end);
        // The period should be approximately 1 year, 1 month, 2 days
        int years = period.get(ChronoPeriod.YEARS);
        int months = period.get(ChronoPeriod.MONTHS);
        int days = period.get(ChronoPeriod.DAYS);
        if (years != 1 || months != 1 || days != 2) {
            throw new AssertionError("Expected period 1 year, 1 month, 2 days but got " + years + " years, " + months + " months, " + days + " days");
        }
    }

    private static void testUntilEarlierDate() {
        MinguoDate start = MinguoDate.of(111, 3, 15); // ISO 2022-03-15
        MinguoDate end = MinguoDate.of(110, 12, 10);  // ISO 2021-12-10
        ChronoPeriod period = start.until(end);
        // The period should be negative
        int years = period.get(ChronoPeriod.YEARS);
        int months = period.get(ChronoPeriod.MONTHS);
        int days = period.get(ChronoPeriod.DAYS);
        if (years >= 0 || months >= 0 || days >= 0) {
            throw new AssertionError("Expected negative period when end date is before start date");
        }
    }
}
