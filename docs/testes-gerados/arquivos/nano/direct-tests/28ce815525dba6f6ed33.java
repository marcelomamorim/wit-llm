/*
 * @test
 * @summary Test IsoChronology.dateYearDay method for valid and invalid inputs
 * @run main IsoChronologyWitupTest
 */

import java.time.chrono.IsoChronology;
import java.time.LocalDate;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        testValidDate();
        testInvalidYear();
        testInvalidDayOfYear();
    }

    static void testValidDate() {
        try {
            LocalDate date = IsoChronology.INSTANCE.dateYearDay(2020, 60);
            if (date.getYear() != 2020 || date.getDayOfYear() != 60) {
                throw new AssertionError("Valid date test failed: " + date);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid date test", e);
        }
    }

    static void testInvalidYear() {
        try {
            IsoChronology.INSTANCE.dateYearDay(Integer.MIN_VALUE, 100);
            // Expecting no exception, as LocalDate.ofYearDay handles large years
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for large year", e);
        }
        try {
            IsoChronology.INSTANCE.dateYearDay(Integer.MAX_VALUE, 100);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for max year", e);
        }
    }

    static void testInvalidDayOfYear() {
        try {
            IsoChronology.INSTANCE.dateYearDay(2020, 0);
            throw new AssertionError("Expected exception for dayOfYear=0 not thrown");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            IsoChronology.INSTANCE.dateYearDay(2020, 366); // 2020 is leap year, so 366 is valid
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for leap year 366", e);
        }
        try {
            IsoChronology.INSTANCE.dateYearDay(2019, 366); // 2019 is not leap year
            throw new AssertionError("Expected exception for non-leap year 366 not thrown");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            IsoChronology.INSTANCE.dateYearDay(2020, 367);
            throw new AssertionError("Expected exception for dayOfYear=367 not thrown");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
