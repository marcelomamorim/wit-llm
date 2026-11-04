/*
 * @test
 * @summary Test for LocalDate.plusYears(long) method, covering normal and edge cases.
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testPlusYearsZero();
        testPlusYearsPositive();
        testPlusYearsNegative();
        testPlusYearsOverflow();
        testPlusYearsUnderflow();
    }

    static void testPlusYearsZero() {
        LocalDate date = LocalDate.of(2020, Month.JANUARY, 15);
        LocalDate result = date.plusYears(0);
        if (!result.equals(date)) {
            throw new AssertionError("plusYears(0) should return the same date");
        }
    }

    static void testPlusYearsPositive() {
        LocalDate date = LocalDate.of(2020, Month.JANUARY, 15);
        LocalDate expected = LocalDate.of(2025, Month.JANUARY, 15);
        LocalDate result = date.plusYears(5);
        if (!result.equals(expected)) {
            throw new AssertionError("plusYears(5) did not produce expected date");
        }
    }

    static void testPlusYearsNegative() {
        LocalDate date = LocalDate.of(2020, Month.JANUARY, 15);
        LocalDate expected = LocalDate.of(2015, Month.JANUARY, 15);
        LocalDate result = date.plusYears(-5);
        if (!result.equals(expected)) {
            throw new AssertionError("plusYears(-5) did not produce expected date");
        }
    }

    static void testPlusYearsOverflow() {
        try {
            LocalDate date = LocalDate.of(Year.MAX_VALUE, Month.JANUARY, 1);
            date.plusYears(1);
            throw new AssertionError("Expected DateTimeException for overflow");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testPlusYearsUnderflow() {
        try {
            LocalDate date = LocalDate.of(Year.MIN_VALUE, Month.JANUARY, 1);
            date.plusYears(-1);
            throw new AssertionError("Expected DateTimeException for underflow");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
