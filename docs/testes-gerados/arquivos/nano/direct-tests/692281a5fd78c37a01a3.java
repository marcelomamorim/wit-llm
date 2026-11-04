/*
 * @test
 * @summary Test lengthOfMonth() method of HijrahDate for various dates.
 * @run main HijrahDateWitupTest
 */

import java.time.chrono.HijrahDate;
import java.time.temporal.ChronoField;

public class HijrahDateWitupTest {

    public static void main(String[] args) {
        testLengthOfMonthForValidDates();
        testLengthOfMonthForEdgeCases();
    }

    static void testLengthOfMonthForValidDates() {
        // Test for a known valid date
        HijrahDate date = HijrahDate.of(1442, 9, 1); // Corresponds to some Hijrah date
        int length = date.lengthOfMonth();
        int expectedLength = date.getChronology().getMonthLength(1442, date.get(ChronoField.MONTH_OF_YEAR));
        if (length != expectedLength) {
            throw new AssertionError("lengthOfMonth() returned " + length + " but expected " + expectedLength);
        }
        // Test for another date
        HijrahDate date2 = HijrahDate.of(1443, 12, 29);
        int length2 = date2.lengthOfMonth();
        int expectedLength2 = date2.getChronology().getMonthLength(1443, date2.get(ChronoField.MONTH_OF_YEAR));
        if (length2 != expectedLength2) {
            throw new AssertionError("lengthOfMonth() returned " + length2 + " but expected " + expectedLength2);
        }
    }

    static void testLengthOfMonthForEdgeCases() {
        // Test for a date at the start of the year
        HijrahDate dateStart = HijrahDate.of(1442, 1, 1);
        int lengthStart = dateStart.lengthOfMonth();
        int expectedStart = dateStart.getChronology().getMonthLength(1442, dateStart.get(ChronoField.MONTH_OF_YEAR));
        if (lengthStart != expectedStart) {
            throw new AssertionError("lengthOfMonth() at start of year returned " + lengthStart + " but expected " + expectedStart);
        }
        // Test for a date at the end of the year
        HijrahDate dateEnd = HijrahDate.of(1442, 12, 29);
        int lengthEnd = dateEnd.lengthOfMonth();
        int expectedEnd = dateEnd.getChronology().getMonthLength(1442, dateEnd.get(ChronoField.MONTH_OF_YEAR));
        if (lengthEnd != expectedEnd) {
            throw new AssertionError("lengthOfMonth() at end of year returned " + lengthEnd + " but expected " + expectedEnd);
        }
    }
}
