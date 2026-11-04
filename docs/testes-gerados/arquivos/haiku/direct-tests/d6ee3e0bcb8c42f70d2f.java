/*
 * @test
 * @summary LocalDate.of(int, int, int) validates year, month and day and returns the expected date
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testValidDates();
        testYearBounds();
        testMonthBounds();
        testDayOfMonthBounds();
        testLeapDayValidation();
        testInvalidDayForMonth();
        System.out.println("PASSED");
    }

    static void testValidDates() {
        LocalDate d = LocalDate.of(2012, 2, 29);
        check(d.getYear() == 2012, "year");
        check(d.getMonthValue() == 2, "month");
        check(d.getDayOfMonth() == 29, "day");

        LocalDate end = LocalDate.of(2018, 12, 31);
        check(end.getYear() == 2018, "end year");
        check(end.getMonthValue() == 12, "end month");
        check(end.getDayOfMonth() == 31, "end day");

        check(LocalDate.of(2018, 1, 1).equals(LocalDate.of(2018, 1, 1)), "equal dates");
    }

    static void testYearBounds() {
        LocalDate min = LocalDate.of(-999999999, 1, 1);
        check(min.getYear() == -999999999, "min year");
        LocalDate max = LocalDate.of(999999999, 12, 31);
        check(max.getYear() == 999999999, "max year");
        expectDateTimeException(() -> LocalDate.of(1000000000, 1, 1), "year above max");
        expectDateTimeException(() -> LocalDate.of(-1000000000, 1, 1), "year below min");
    }

    static void testMonthBounds() {
        expectDateTimeException(() -> LocalDate.of(2018, 0, 1), "month 0");
        expectDateTimeException(() -> LocalDate.of(2018, 13, 1), "month 13");
        check(LocalDate.of(2018, 1, 1).getMonthValue() == 1, "january");
        check(LocalDate.of(2018, 12, 1).getMonthValue() == 12, "december");
    }

    static void testDayOfMonthBounds() {
        expectDateTimeException(() -> LocalDate.of(2018, 1, 0), "day 0");
        expectDateTimeException(() -> LocalDate.of(2018, 1, 32), "day 32");
        check(LocalDate.of(2018, 1, 1).getDayOfMonth() == 1, "first day");
    }

    static void testLeapDayValidation() {
        check(LocalDate.of(2000, 2, 29).getDayOfMonth() == 29, "2000 is leap");
        check(LocalDate.of(2024, 2, 29).getDayOfMonth() == 29, "2024 is leap");
        expectDateTimeException(() -> LocalDate.of(2011, 2, 29), "2011 not leap");
        expectDateTimeException(() -> LocalDate.of(1900, 2, 29), "1900 not leap");
    }

    static void testInvalidDayForMonth() {
        expectDateTimeException(() -> LocalDate.of(2000, 4, 31), "april 31");
        expectDateTimeException(() -> LocalDate.of(2000, 6, 31), "june 31");
        expectDateTimeException(() -> LocalDate.of(2000, 2, 30), "february 30");
        check(LocalDate.of(2000, 4, 30).getDayOfMonth() == 30, "april 30 valid");
    }

    interface Action {
        void run();
    }

    static void expectDateTimeException(Action action, String what) {
        try {
            action.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("Expected DateTimeException: " + what);
    }

    static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + what);
        }
    }
}
