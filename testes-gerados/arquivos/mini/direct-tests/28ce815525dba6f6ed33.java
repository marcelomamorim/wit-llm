/*
 * @test
 * @summary Test java.time.chrono.IsoChronology.dateYearDay(int, int) method
 * @run main IsoChronologyWitupTest
 */

import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.DateTimeException;

public class IsoChronologyWitupTest {
    public static void main(String[] args) {
        testValidDateYearDay();
        testInvalidDateYearDay();
    }

    private static void testValidDateYearDay() {
        IsoChronology iso = IsoChronology.INSTANCE;
        // Normal date
        LocalDate date = iso.dateYearDay(2018, 100);
        if (date.getYear() != 2018 || date.getDayOfYear() != 100) {
            throw new AssertionError("Failed for valid dateYearDay 2018, 100");
        }
        // Leap year date
        LocalDate leapDate = iso.dateYearDay(2020, 366);
        if (leapDate.getYear() != 2020 || leapDate.getDayOfYear() != 366) {
            throw new AssertionError("Failed for leap year dateYearDay 2020, 366");
        }
    }

    private static void testInvalidDateYearDay() {
        IsoChronology iso = IsoChronology.INSTANCE;
        // dayOfYear zero (invalid)
        try {
            iso.dateYearDay(2018, 0);
            throw new AssertionError("Expected DateTimeException for dayOfYear=0");
        } catch (DateTimeException expected) {
        }
        // dayOfYear too large for non-leap year
        try {
            iso.dateYearDay(2019, 366);
            throw new AssertionError("Expected DateTimeException for dayOfYear=366 in non-leap year");
        } catch (DateTimeException expected) {
        }
        // negative year with valid dayOfYear
        LocalDate date = iso.dateYearDay(-1, 1);
        if (date.getYear() != -1 || date.getDayOfYear() != 1) {
            throw new AssertionError("Failed for negative year -1, dayOfYear 1");
        }
    }
}
