/*
 * @test
 * @summary Test java.time.chrono.ThaiBuddhistDate.until(ChronoLocalDate) method
 * @run main ThaiBuddhistDateWitupTest
 */

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.chrono.ThaiBuddhistDate;
import java.time.LocalDate;

public class ThaiBuddhistDateWitupTest {
    public static void main(String[] args) {
        testUntilSameDate();
        testUntilLaterDate();
        testUntilEarlierDate();
    }

    private static void testUntilSameDate() {
        ThaiBuddhistDate date = ThaiBuddhistDate.of(2562, 5, 20); // corresponds to 2019-05-20 ISO
        ChronoPeriod period = date.until(date);
        if (period.getYears() != 0 || period.getMonths() != 0 || period.getDays() != 0) {
            throw new AssertionError("Expected zero period when until same date, got: " + period);
        }
    }

    private static void testUntilLaterDate() {
        ThaiBuddhistDate start = ThaiBuddhistDate.of(2562, 5, 20); // 2019-05-20
        ThaiBuddhistDate end = ThaiBuddhistDate.of(2563, 6, 25);   // 2020-06-25
        ChronoPeriod period = start.until(end);
        // The period should be approximately 1 year, 1 month, 5 days
        if (period.getYears() != 1) {
            throw new AssertionError("Expected 1 year, got: " + period.getYears());
        }
        if (period.getMonths() != 1) {
            throw new AssertionError("Expected 1 month, got: " + period.getMonths());
        }
        if (period.getDays() != 5) {
            throw new AssertionError("Expected 5 days, got: " + period.getDays());
        }
    }

    private static void testUntilEarlierDate() {
        ThaiBuddhistDate start = ThaiBuddhistDate.of(2563, 6, 25); // 2020-06-25
        ThaiBuddhistDate end = ThaiBuddhistDate.of(2562, 5, 20);   // 2019-05-20
        ChronoPeriod period = start.until(end);
        // The period should be negative: -1 year, -1 month, -5 days
        if (period.getYears() != -1) {
            throw new AssertionError("Expected -1 year, got: " + period.getYears());
        }
        if (period.getMonths() != -1) {
            throw new AssertionError("Expected -1 month, got: " + period.getMonths());
        }
        if (period.getDays() != -5) {
            throw new AssertionError("Expected -5 days, got: " + period.getDays());
        }
    }
}
