/*
 * @test
 * @summary Tests LocalDate.withDayOfYear for same-day identity, valid day-of-year changes, leap-year boundary and invalid day-of-year values
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testSameDayOfYearReturnsSameInstance();
        testValidDayOfYearChangesDate();
        testLeapYearDay366();
        testNonLeapYearDay366Rejected();
        testDayOfYearZeroRejected();
        testDayOfYearOutOfRangeRejected();
        testNegativeDayOfYearRejected();
    }

    static void testSameDayOfYearReturnsSameInstance() {
        LocalDate date = LocalDate.of(2018, 3, 15);
        int doy = date.getDayOfYear();
        LocalDate result = date.withDayOfYear(doy);
        if (result != date) {
            throw new AssertionError("expected same instance when day-of-year is unchanged");
        }
    }

    static void testValidDayOfYearChangesDate() {
        LocalDate result = LocalDate.of(2018, 1, 1).withDayOfYear(32);
        check(LocalDate.of(2018, 2, 1), result, "withDayOfYear(32) on 2018-01-01");

        result = LocalDate.of(2018, 6, 10).withDayOfYear(1);
        check(LocalDate.of(2018, 1, 1), result, "withDayOfYear(1) on 2018-06-10");

        result = LocalDate.of(2018, 1, 1).withDayOfYear(365);
        check(LocalDate.of(2018, 12, 31), result, "withDayOfYear(365) on 2018");
    }

    static void testLeapYearDay366() {
        LocalDate result = LocalDate.of(2016, 1, 1).withDayOfYear(366);
        check(LocalDate.of(2016, 12, 31), result, "withDayOfYear(366) in leap year 2016");
    }

    static void testNonLeapYearDay366Rejected() {
        expectDateTimeException(() -> LocalDate.of(2018, 1, 1).withDayOfYear(366), "day 366 in non-leap 2018");
    }

    static void testDayOfYearZeroRejected() {
        expectDateTimeException(() -> LocalDate.of(2018, 1, 1).withDayOfYear(0), "day 0");
    }

    static void testDayOfYearOutOfRangeRejected() {
        expectDateTimeException(() -> LocalDate.of(2016, 1, 1).withDayOfYear(367), "day 367");
    }

    static void testNegativeDayOfYearRejected() {
        expectDateTimeException(() -> LocalDate.of(2018, 1, 1).withDayOfYear(-1), "day -1");
    }

    static void check(LocalDate expected, LocalDate actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + ": expected " + expected + " but was " + actual);
        }
    }

    interface Action {
        void run();
    }

    static void expectDateTimeException(Action action, String message) {
        try {
            action.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new AssertionError("expected DateTimeException for " + message);
    }
}
