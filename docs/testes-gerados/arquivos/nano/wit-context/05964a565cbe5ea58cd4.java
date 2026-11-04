/*
 * @test
 * @summary Tests for java.time.YearMonth.atDay(int)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.LocalDate;
import java.time.DateTimeException;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testValidAtDay();
        testInvalidAtDay();
        testEdgeCases();
    }

    static void testValidAtDay() {
        try {
            YearMonth ym = YearMonth.of(2020, 2); // leap year
            LocalDate date = ym.atDay(29);
            if (!date.equals(LocalDate.of(2020, 2, 29))) {
                throw new AssertionError("Expected 2020-02-29, got " + date);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testValidAtDay", e);
        }
    }

    static void testInvalidAtDay() {
        // Test day 30 in February of a leap year (should succeed)
        try {
            YearMonth ym = YearMonth.of(2020, 2);
            LocalDate.of(2020, 2, 30);
            throw new AssertionError("Expected DateTimeException for invalid day 30 in Feb 2020");
        } catch (DateTimeException e) {
            // expected
        }
        // Test day 29 in February of a non-leap year (should fail)
        try {
            YearMonth ym = YearMonth.of(2019, 2);
            ym.atDay(29);
            throw new AssertionError("Expected DateTimeException for invalid day 29 in Feb 2019");
        } catch (DateTimeException e) {
            // expected
        }
        // Test day 32 in January (should fail)
        try {
            YearMonth ym = YearMonth.of(2021, 1);
            ym.atDay(32);
            throw new AssertionError("Expected DateTimeException for invalid day 32 in Jan 2021");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testEdgeCases() {
        // Test minimum valid day (1)
        try {
            YearMonth ym = YearMonth.of(2021, 3);
            LocalDate date = ym.atDay(1);
            if (!date.equals(LocalDate.of(2021, 3, 1))) {
                throw new AssertionError("Expected 2021-03-01, got " + date);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for day 1", e);
        }
        // Test maximum valid day (31 for months with 31 days)
        try {
            YearMonth ym = YearMonth.of(2021, 1);
            LocalDate date = ym.atDay(31);
            if (!date.equals(LocalDate.of(2021, 1, 31))) {
                throw new AssertionError("Expected 2021-01-31, got " + date);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for day 31", e);
        }
    }
}
