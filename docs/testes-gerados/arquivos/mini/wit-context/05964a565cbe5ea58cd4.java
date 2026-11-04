/*
 * @test
 * @summary Test YearMonth.atDay(int) method for valid and invalid dayOfMonth inputs
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.LocalDate;
import java.time.DateTimeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testValidAtDay();
        testAtDayInvalidDayTooLow();
        testAtDayInvalidDayTooHigh();
    }

    private static void testValidAtDay() {
        YearMonth ym = YearMonth.of(2020, 2); // Leap year February
        LocalDate ld = ym.atDay(29);
        if (ld.getYear() != 2020 || ld.getMonthValue() != 2 || ld.getDayOfMonth() != 29) {
            throw new AssertionError("atDay did not return expected LocalDate for valid day");
        }
    }

    private static void testAtDayInvalidDayTooLow() {
        YearMonth ym = YearMonth.of(2020, 1);
        try {
            ym.atDay(0); // dayOfMonth too low
            throw new AssertionError("Expected DateTimeException for dayOfMonth=0");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    private static void testAtDayInvalidDayTooHigh() {
        YearMonth ym = YearMonth.of(2019, 4); // April has 30 days
        try {
            ym.atDay(31); // dayOfMonth too high
            throw new AssertionError("Expected DateTimeException for dayOfMonth=31");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
