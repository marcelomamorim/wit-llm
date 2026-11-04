/*
 * @test
 * @summary Test YearMonth.isValidDay(int) method for valid and invalid days
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.DateTimeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testValidDays();
        testInvalidDays();
        testInvalidMonthTriggersException();
    }

    private static void testValidDays() {
        YearMonth ym = YearMonth.of(2020, 2); // Leap year February
        // Valid days in February 2020: 1 to 29
        for (int day = 1; day <= 29; day++) {
            if (!ym.isValidDay(day)) {
                throw new AssertionError("Expected valid day: " + day);
            }
        }
        // Day 0 and 30 should be invalid
        if (ym.isValidDay(0)) {
            throw new AssertionError("Day 0 should be invalid");
        }
        if (ym.isValidDay(30)) {
            throw new AssertionError("Day 30 should be invalid");
        }
    }

    private static void testInvalidDays() {
        YearMonth ym = YearMonth.of(2019, 4); // April has 30 days
        // Valid days: 1 to 30
        if (ym.isValidDay(31)) {
            throw new AssertionError("Day 31 should be invalid for April");
        }
        if (ym.isValidDay(-1)) {
            throw new AssertionError("Negative day should be invalid");
        }
    }

    private static void testInvalidMonthTriggersException() {
        // The isValidDay method calls lengthOfMonth internally, which calls getMonth(),
        // which calls Month.of(int). Month.of throws DateTimeException if month < 1 or > 12.
        // We test that YearMonth.of throws DateTimeException for invalid month,
        // so that isValidDay cannot be called on invalid YearMonth instances.
        try {
            YearMonth.of(2020, 0);
            throw new AssertionError("Expected DateTimeException for month 0");
        } catch (DateTimeException expected) {
        }
        try {
            YearMonth.of(2020, 13);
            throw new AssertionError("Expected DateTimeException for month 13");
        } catch (DateTimeException expected) {
        }
    }
}
