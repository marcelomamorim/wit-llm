/*
 * @test
 * @summary Test java.time.LocalDate.withMonth(int) method behavior
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testWithMonthSameMonthReturnsThis();
        testWithMonthValidChange();
        testWithMonthInvalidLow();
        testWithMonthInvalidHigh();
    }

    static void testWithMonthSameMonthReturnsThis() {
        LocalDate date = LocalDate.of(2018, 5, 15);
        LocalDate result = date.withMonth(5);
        if (result != date) {
            throw new AssertionError("Expected same instance when month is unchanged");
        }
    }

    static void testWithMonthValidChange() {
        LocalDate date = LocalDate.of(2018, 1, 31);
        LocalDate changed = date.withMonth(2);
        // February 2018 has 28 days, so day should be adjusted to 28
        if (changed.getYear() != 2018 || changed.getMonthValue() != 2 || changed.getDayOfMonth() != 28) {
            throw new AssertionError("Expected adjusted date to 2018-02-28 but got " + changed);
        }
    }

    static void testWithMonthInvalidLow() {
        LocalDate date = LocalDate.of(2018, 1, 15);
        try {
            date.withMonth(0);
            throw new AssertionError("Expected DateTimeException for month=0");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testWithMonthInvalidHigh() {
        LocalDate date = LocalDate.of(2018, 1, 15);
        try {
            date.withMonth(13);
            throw new AssertionError("Expected DateTimeException for month=13");
        } catch (DateTimeException e) {
            // expected
        }
    }
}