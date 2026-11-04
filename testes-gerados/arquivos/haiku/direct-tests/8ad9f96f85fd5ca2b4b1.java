/*
 * @test
 * @summary Witup-style tests for LocalDate.withMonth observable behavior
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        sameMonthReturnsSameInstance();
        clampsDayToShorterMonth();
        clampsDayInLeapYear();
        clampsDayInNonLeapCentury();
        keepsDayWhenMonthHasEnoughDays();
        invalidMonthZeroRejected();
        invalidMonthThirteenRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void sameMonthReturnsSameInstance() {
        LocalDate d = LocalDate.of(2020, 1, 15);
        check(d.withMonth(1) == d, "withMonth with current month must return this");
    }

    static void clampsDayToShorterMonth() {
        LocalDate d = LocalDate.of(2021, 3, 31).withMonth(4);
        check(d.equals(LocalDate.of(2021, 4, 30)), "31 March to April must clamp to 30 April, got " + d);

        LocalDate e = LocalDate.of(2021, 1, 31).withMonth(2);
        check(e.equals(LocalDate.of(2021, 2, 28)), "31 January to February must clamp to 28 February, got " + e);

        LocalDate f = LocalDate.of(2021, 11, 30).withMonth(12);
        check(f.equals(LocalDate.of(2021, 12, 30)), "30 November to December must keep 30 December, got " + f);
    }

    static void clampsDayInLeapYear() {
        LocalDate d = LocalDate.of(2000, 1, 31).withMonth(2);
        check(d.equals(LocalDate.of(2000, 2, 29)), "2000 is leap year, expected 29 February, got " + d);
    }

    static void clampsDayInNonLeapCentury() {
        LocalDate d = LocalDate.of(1900, 1, 31).withMonth(2);
        check(d.equals(LocalDate.of(1900, 2, 28)), "1900 is not leap year, expected 28 February, got " + d);
    }

    static void keepsDayWhenMonthHasEnoughDays() {
        LocalDate d = LocalDate.of(2021, 1, 10).withMonth(5);
        check(d.getYear() == 2021, "year must be preserved");
        check(d.getMonthValue() == 5, "month must be 5, got " + d.getMonthValue());
        check(d.getDayOfMonth() == 10, "day must be preserved, got " + d.getDayOfMonth());
    }

    static void invalidMonthZeroRejected() {
        boolean thrown = false;
        try {
            LocalDate.of(2020, 1, 15).withMonth(0);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "withMonth(0) must throw DateTimeException");
    }

    static void invalidMonthThirteenRejected() {
        boolean thrown = false;
        try {
            LocalDate.of(2020, 1, 15).withMonth(13);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "withMonth(13) must throw DateTimeException");
    }
}
