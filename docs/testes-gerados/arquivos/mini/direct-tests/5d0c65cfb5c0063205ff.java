/*
 * @test
 * @summary Test java.time.LocalDate.until(ChronoLocalDate) method behavior
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testUntilSameDate();
        testUntilPositiveMonthsAndNegativeDays();
        testUntilNegativeMonthsAndPositiveDays();
        testUntilPositiveMonthsAndPositiveDays();
        testUntilNegativeMonthsAndNegativeDays();
    }

    private static void testUntilSameDate() {
        LocalDate start = LocalDate.of(2020, 5, 15);
        ChronoLocalDate end = LocalDate.of(2020, 5, 15);
        Period p = start.until(end);
        if (p.getYears() != 0 || p.getMonths() != 0 || p.getDays() != 0) {
            throw new AssertionError("Expected zero period for same dates but got: " + p);
        }
    }

    private static void testUntilPositiveMonthsAndNegativeDays() {
        LocalDate start = LocalDate.of(2020, 1, 31);
        ChronoLocalDate end = LocalDate.of(2020, 3, 1);
        Period p = start.until(end);
        // totalMonths = 2 (Jan to Mar), days = 1 - 31 = -30, so totalMonths-- => 1, days recalculated
        if (p.getYears() != 0 || p.getMonths() != 1 || p.getDays() != 1) {
            throw new AssertionError("Expected P0Y1M1D but got: " + p);
        }
    }

    private static void testUntilNegativeMonthsAndPositiveDays() {
        LocalDate start = LocalDate.of(2020, 3, 1);
        ChronoLocalDate end = LocalDate.of(2020, 1, 31);
        Period p = start.until(end);
        // totalMonths = -2, days = 31 - 1 = 30 > 0, so totalMonths++ => -1, days -= lengthOfMonth(end) = 31
        // days = 30 - 31 = -1
        if (p.getYears() != 0 || p.getMonths() != -1 || p.getDays() != -1) {
            throw new AssertionError("Expected P0Y-1M-1D but got: " + p);
        }
    }

    private static void testUntilPositiveMonthsAndPositiveDays() {
        LocalDate start = LocalDate.of(2020, 1, 10);
        ChronoLocalDate end = LocalDate.of(2020, 3, 15);
        Period p = start.until(end);
        // totalMonths = 2, days = 15 - 10 = 5, no adjustment
        if (p.getYears() != 0 || p.getMonths() != 2 || p.getDays() != 5) {
            throw new AssertionError("Expected P0Y2M5D but got: " + p);
        }
    }

    private static void testUntilNegativeMonthsAndNegativeDays() {
        LocalDate start = LocalDate.of(2020, 3, 15);
        ChronoLocalDate end = LocalDate.of(2020, 1, 10);
        Period p = start.until(end);
        // totalMonths = -2, days = 10 - 15 = -5, no adjustment
        if (p.getYears() != 0 || p.getMonths() != -2 || p.getDays() != -5) {
            throw new AssertionError("Expected P0Y-2M-5D but got: " + p);
        }
    }
}