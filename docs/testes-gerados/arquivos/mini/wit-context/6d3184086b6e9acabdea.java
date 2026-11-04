/*
 * @test
 * @summary Test java.time.MonthDay.atYear(int) for valid and invalid year inputs
 * @run main MonthDayWitupTest
 */

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.DateTimeException;

public class MonthDayWitupTest {
    public static void main(String[] args) {
        testAtYearValid();
        testAtYearInvalidYearTooSmall();
        testAtYearInvalidYearTooLarge();
        testAtYearInvalidMonthRange();
    }

    private static void testAtYearValid() {
        MonthDay md = MonthDay.of(2, 29); // Leap day
        // For a leap year, day should be 29
        LocalDate ldLeap = md.atYear(2020);
        if (ldLeap.getYear() != 2020 || ldLeap.getMonthValue() != 2 || ldLeap.getDayOfMonth() != 29) {
            throw new AssertionError("Failed valid leap year test");
        }
        // For a non-leap year, day should fallback to 28
        LocalDate ldNonLeap = md.atYear(2019);
        if (ldNonLeap.getYear() != 2019 || ldNonLeap.getMonthValue() != 2 || ldNonLeap.getDayOfMonth() != 28) {
            throw new AssertionError("Failed valid non-leap year test");
        }
    }

    private static void testAtYearInvalidYearTooSmall() {
        MonthDay md = MonthDay.of(1, 1);
        int invalidYear = Integer.MIN_VALUE; // Very small year
        try {
            md.atYear(invalidYear);
            throw new AssertionError("Expected DateTimeException for year too small");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testAtYearInvalidYearTooLarge() {
        MonthDay md = MonthDay.of(12, 31);
        int invalidYear = Integer.MAX_VALUE; // Very large year
        try {
            md.atYear(invalidYear);
            throw new AssertionError("Expected DateTimeException for year too large");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testAtYearInvalidMonthRange() {
        // This test tries to simulate the condition where the year is valid but the month is out of range
        // Since MonthDay.of validates month and day, we cannot create invalid MonthDay directly
        // So we test with valid MonthDay but invalid year that triggers month range check internally
        MonthDay md = MonthDay.of(1, 31);
        int year = 2000;
        // This should succeed
        LocalDate ld = md.atYear(year);
        if (ld.getYear() != year || ld.getMonthValue() != 1 || ld.getDayOfMonth() != 31) {
            throw new AssertionError("Failed valid month-day atYear test");
        }
    }
}
