/*
 * @test
 * @summary Test JapaneseDate.until(ChronoLocalDate) method behavior
 * @run main JapaneseDateWitupTest
 */

import java.time.chrono.JapaneseDate;
import java.time.chrono.JapaneseEra;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.Period;

public class JapaneseDateWitupTest {
    public static void main(String[] args) {
        testUntilSameDate();
        testUntilLaterDate();
        testUntilEarlierDate();
    }

    private static void testUntilSameDate() {
        JapaneseDate date = JapaneseDate.of(JapaneseEra.HEISEI, 30, 4, 30);
        ChronoPeriod period = date.until(date);
        if (period.getYears() != 0 || period.getMonths() != 0 || period.getDays() != 0) {
            throw new AssertionError("Expected zero period when until called with same date");
        }
    }

    private static void testUntilLaterDate() {
        JapaneseDate start = JapaneseDate.of(JapaneseEra.HEISEI, 30, 4, 30);
        JapaneseDate end = JapaneseDate.of(JapaneseEra.HEISEI, 31, 5, 1);
        ChronoPeriod period = start.until(end);
        // The period should be positive
        if (period.getYears() < 0 || period.getMonths() < 0 || period.getDays() < 0) {
            throw new AssertionError("Expected positive period when endDate is after startDate");
        }
        // Check that the period matches the difference in ISO dates
        Period isoPeriod = start.toLocalDate().until(end.toLocalDate());
        if (period.getYears() != isoPeriod.getYears() ||
            period.getMonths() != isoPeriod.getMonths() ||
            period.getDays() != isoPeriod.getDays()) {
            throw new AssertionError("Period components do not match ISO date difference");
        }
    }

    private static void testUntilEarlierDate() {
        JapaneseDate start = JapaneseDate.of(JapaneseEra.HEISEI, 31, 5, 1);
        JapaneseDate end = JapaneseDate.of(JapaneseEra.HEISEI, 30, 4, 30);
        ChronoPeriod period = start.until(end);
        // The period should be negative or zero
        if (period.getYears() > 0 || period.getMonths() > 0 || period.getDays() > 0) {
            throw new AssertionError("Expected non-positive period when endDate is before startDate");
        }
        // Check that the period matches the difference in ISO dates
        Period isoPeriod = start.toLocalDate().until(end.toLocalDate());
        if (period.getYears() != isoPeriod.getYears() ||
            period.getMonths() != isoPeriod.getMonths() ||
            period.getDays() != isoPeriod.getDays()) {
            throw new AssertionError("Period components do not match ISO date difference");
        }
    }
}
