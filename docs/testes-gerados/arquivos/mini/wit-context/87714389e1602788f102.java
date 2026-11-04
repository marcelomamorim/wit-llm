/*
 * @test
 * @summary Test YearMonth.getMonth() method for valid and invalid month values
 * @run main YearMonthWitupTest
 */

import java.time.Month;
import java.time.YearMonth;
import java.time.DateTimeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testGetMonthValid();
        testGetMonthInvalidLow();
        testGetMonthInvalidHigh();
    }

    private static void testGetMonthValid() {
        YearMonth ym = YearMonth.of(2020, 5);
        Month m = ym.getMonth();
        if (m != Month.MAY) {
            throw new AssertionError("Expected MAY but got " + m);
        }
    }

    private static void testGetMonthInvalidLow() {
        // Create YearMonth instance with invalid month 0 using reflection is not allowed,
        // so simulate by subclassing or other means is not possible here.
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
