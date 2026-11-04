/*
 * @test
 * @summary Test java.time.MonthDay.getMonth() method for valid and invalid month values
 * @run main MonthDayWitupTest
 */

import java.time.MonthDay;
import java.time.Month;
import java.time.DateTimeException;

public class MonthDayWitupTest {
    public static void main(String[] args) {
        testGetMonthValid();
        testGetMonthInvalidLow();
        testGetMonthInvalidHigh();
    }

    private static void testGetMonthValid() {
        // Valid month values from 1 to 12 should return correct Month enum
        for (int m = 1; m <= 12; m++) {
            MonthDay md = MonthDay.of(m, 1); // day 1 is valid for all months
            Month month = md.getMonth();
            if (month.getValue() != m) {
                throw new AssertionError("Expected month value " + m + " but got " + month.getValue());
            }
        }
    }

    private static void testGetMonthInvalidLow() {
        // Construct MonthDay with invalid month 0 via reflection is not possible,
        // so test behavior by creating MonthDay with valid month and forcibly
        // testing getMonth() with invalid internal state is not possible publicly.
        // Instead, test that Month.of throws DateTimeException for invalid month 0.
        try {
            Month.of(0);
            throw new AssertionError("Expected DateTimeException for month 0");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    private static void testGetMonthInvalidHigh() {
        // Similarly test Month.of with invalid month 13
        try {
            Month.of(13);
            throw new AssertionError("Expected DateTimeException for month 13");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
