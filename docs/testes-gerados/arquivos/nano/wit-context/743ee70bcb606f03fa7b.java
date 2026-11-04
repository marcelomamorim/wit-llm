/*
 * @test
 * @summary Tests for java.time.LocalDate.of(int, Month, int)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testValidDate();
        testNullMonth();
        testYearTooSmall();
        testYearTooLarge();
        testDayOfMonthTooSmall();
        testDayOfMonthTooLarge();
    }

    static void testValidDate() {
        try {
            LocalDate date = LocalDate.of(2020, Month.FEBRUARY, 29);
            if (date.getYear() != 2020 || date.getMonth() != Month.FEBRUARY || date.getDayOfMonth() != 29) {
                throw new AssertionError("Valid date test failed");
            }
        } catch (Exception e) {
            throw new AssertionError("Valid date test threw exception", e);
        }
    }

    static void testNullMonth() {
        try {
            LocalDate.of(2020, null, 15);
            throw new AssertionError("Null month did not throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Null month test threw wrong exception", e);
        }
    }

    static void testYearTooSmall() {
        try {
            // Assuming YEAR.checkValidValue throws DateTimeException for invalid years
            LocalDate.of(Integer.MIN_VALUE, Month.JANUARY, 1);
            // If no exception, test fails
            throw new AssertionError("Year too small did not throw exception");
        } catch (java.time.DateTimeException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Year too small test threw wrong exception", e);
        }
    }

    static void testYearTooLarge() {
        try {
            // Assuming YEAR.checkValidValue throws DateTimeException for invalid years
            LocalDate.of(999999999, Month.JANUARY, 1);
            throw new AssertionError("Year too large did not throw exception");
        } catch (java.time.DateTimeException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Year too large test threw wrong exception", e);
        }
    }

    static void testDayOfMonthTooSmall() {
        try {
            LocalDate.of(2020, Month.MARCH, 0);
            throw new AssertionError("Day of month too small did not throw exception");
        } catch (java.time.DateTimeException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Day of month too small test threw wrong exception", e);
        }
    }

    static void testDayOfMonthTooLarge() {
        try {
            LocalDate.of(2020, Month.FEBRUARY, 30);
            throw new AssertionError("Day of month too large did not throw exception");
        } catch (java.time.DateTimeException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Day of month too large test threw wrong exception", e);
        }
    }
}
