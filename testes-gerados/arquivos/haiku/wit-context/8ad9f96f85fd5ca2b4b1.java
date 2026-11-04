/*
 * @test
 * @summary Witup-generated checks for LocalDate.withMonth(int): valid month changes, same-month identity, and DateTimeException for out-of-range months
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testValidMonthChange();
        testMonthClampsDayToEndOfMonth();
        testSameMonthReturnsSameInstance();
        testMonthZeroThrows();
        testMonthThirteenThrows();
        testNegativeMonthThrows();
        testMaxIntMonthThrows();
    }

    private static void testValidMonthChange() {
        LocalDate date = LocalDate.of(2020, 1, 15);
        LocalDate result = date.withMonth(6);
        check(result.equals(LocalDate.of(2020, 6, 15)), "withMonth(6) on 2020-01-15 should be 2020-06-15 but was " + result);
    }

    private static void testMonthClampsDayToEndOfMonth() {
        LocalDate date = LocalDate.of(2020, 1, 31);
        LocalDate result = date.withMonth(2);
        check(result.equals(LocalDate.of(2020, 2, 29)), "withMonth(2) on 2020-01-31 should be 2020-02-29 but was " + result);

        LocalDate nonLeap = LocalDate.of(2021, 3, 31).withMonth(2);
        check(nonLeap.equals(LocalDate.of(2021, 2, 28)), "withMonth(2) on 2021-03-31 should be 2021-02-28 but was " + nonLeap);
    }

    private static void testSameMonthReturnsSameInstance() {
        LocalDate date = LocalDate.of(2020, 5, 10);
        LocalDate result = date.withMonth(5);
        check(result == date, "withMonth with the same month should return this");
    }

    private static void testMonthZeroThrows() {
        expectDateTimeException(LocalDate.of(2020, 1, 15), 0);
    }

    private static void testMonthThirteenThrows() {
        expectDateTimeException(LocalDate.of(2020, 1, 15), 13);
    }

    private static void testNegativeMonthThrows() {
        expectDateTimeException(LocalDate.of(2020, 1, 15), -1);
    }

    private static void testMaxIntMonthThrows() {
        expectDateTimeException(LocalDate.of(2020, 1, 15), Integer.MAX_VALUE);
    }

    private static void expectDateTimeException(LocalDate date, int month) {
        try {
            LocalDate result = date.withMonth(month);
            throw new RuntimeException("Expected DateTimeException for withMonth(" + month + ") but got " + result);
        } catch (DateTimeException expected) {
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
