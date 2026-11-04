/* @test    @summary Test the until method of ThaiBuddhistDate for various date comparisons    @run main ThaiBuddhistDateWitupTest */

import static java.time.temporal.ChronoField.*;
import static org.testng.Assert.*;

import java.time.chrono.ThaiBuddhistDate;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.chrono.ChronoLocalDate;
import java.time.Period;
import java.time.chrono.ChronoPeriod;

public class ThaiBuddhistDateWitupTest {

    public static void main(String[] args) {
        testUntilSameDate();
        testUntilDifferentDates();
        testUntilWithNull();
    }

    static void testUntilSameDate() {
        ThaiBuddhistDate date1 = ThaiBuddhistDate.of(2565, 5, 15);
        ChronoPeriod period = date1.until(date1);
        assertEquals(period.getYears(), 0, "Years should be 0 for same date");
        assertEquals(period.getMonths(), 0, "Months should be 0 for same date");
        assertEquals(period.getDays(), 0, "Days should be 0 for same date");
    }

    static void testUntilDifferentDates() {
        ThaiBuddhistDate date1 = ThaiBuddhistDate.of(2564, 12, 31);
        ThaiBuddhistDate date2 = ThaiBuddhistDate.of(2565, 1, 1);
        ChronoPeriod period = date1.until(date2);
        assertEquals(period.getYears(), 1, "Year difference");
        assertEquals(period.getMonths(), 0, "Month difference");
        assertEquals(period.getDays(), 1, "Day difference");
    }

    static void testUntilWithNull() {
        ThaiBuddhistDate date1 = ThaiBuddhistDate.of(2564, 6, 15);
        try {
            date1.until(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
