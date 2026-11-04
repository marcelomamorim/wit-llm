/*
 * @test
 * @summary Tests for HijrahDate.of method to ensure correct date creation and exception handling.
 * @run main HijrahDateWitupTest
 */

import java.time.chrono.HijrahDate;
import java.time.chrono.HijrahChronology;
import java.time.DateTimeException;

public class HijrahDateWitupTest {

    public static void main(String[] args) {
        testValidDate();
        testInvalidMonth();
        testInvalidDay();
    }

    static void testValidDate() {
        int year = 1442;
        int month = 9;
        int day = 15;
        HijrahDate date = HijrahDate.of(year, month, day);
        assert date != null : "Expected non-null HijrahDate";
        assert date.getChronology() == HijrahChronology.INSTANCE : "Expected HijrahChronology";
        assert date.getYear() == year : "Year mismatch";
        assert date.getMonthValue() == month : "Month mismatch";
        assert date.getDayOfMonth() == day : "Day mismatch";
    }

    static void testInvalidMonth() {
        int year = 1442;
        int invalidMonth = 13; // Invalid month
        int day = 10;
        try {
            HijrahDate.of(year, invalidMonth, day);
            assert false : "Expected DateTimeException for invalid month";
        } catch (DateTimeException e) {
            // Expected exception
        }
    }

    static void testInvalidDay() {
        int year = 1442;
        int month = 9;
        int invalidDay = 32; // Invalid day
        try {
            HijrahDate.of(year, month, invalidDay);
            assert false : "Expected DateTimeException for invalid day";
        } catch (DateTimeException e) {
            // Expected exception
        }
    }
}
