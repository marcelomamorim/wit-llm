/*
 * @test
 * @summary Test YearMonth.lengthOfMonth() for valid and invalid month values
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.time.DateTimeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testLengthOfMonthValid();
        testLengthOfMonthInvalidMonth();
    }

    private static void testLengthOfMonthValid() {
        // Test known month lengths for a non-leap year
        YearMonth ymJan = YearMonth.of(2019, 1); // January
        if (ymJan.lengthOfMonth() != 31) {
            throw new AssertionError("January should have 31 days");
        }
        YearMonth ymFebNonLeap = YearMonth.of(2019, 2); // February non-leap
        if (ymFebNonLeap.lengthOfMonth() != 28) {
            throw new AssertionError("February 2019 should have 28 days");
        }
        YearMonth ymFebLeap = YearMonth.of(2020, 2); // February leap year
        if (ymFebLeap.lengthOfMonth() != 29) {
            throw new AssertionError("February 2020 should have 29 days");
        }
        YearMonth ymApr = YearMonth.of(2019, 4); // April
        if (ymApr.lengthOfMonth() != 30) {
            throw new AssertionError("April should have 30 days");
        }
    }

    private static void testLengthOfMonthInvalidMonth() {
        // The method lengthOfMonth() calls getMonth() internally, which throws DateTimeException
        // if the month is invalid. Since YearMonth.of() validates month, we cannot create
        // YearMonth with invalid month directly. So we test the exception indirectly by
        // attempting to create YearMonth with invalid month and expecting DateTimeException.
        try {
            YearMonth.of(2019, 0); // invalid month 0
            throw new AssertionError("Expected DateTimeException for month 0");
        } catch (DateTimeException expected) {
            // expected
        }
        try {
            YearMonth.of(2019, 13); // invalid month 13
            throw new AssertionError("Expected DateTimeException for month 13");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
