/*
 * @test
 * @summary Test HijrahChronology.dateEpochDay for various epochDay values
 * @run main HijrahChronologyWitupTest
 */

import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;

public class HijrahChronologyWitupTest {

    public static void main(String[] args) {
        testDateEpochDayWithZero();
        testDateEpochDayWithPositive();
        testDateEpochDayWithNegative();
    }

    static void testDateEpochDayWithZero() {
        HijrahChronology chrono = HijrahChronology.INSTANCE;
        HijrahDate date = chrono.dateEpochDay(0L);
        long epochDay = date.toEpochDay();
        if (epochDay != 0L) {
            throw new AssertionError("Expected epochDay 0, but got " + epochDay);
        }
    }

    static void testDateEpochDayWithPositive() {
        HijrahChronology chrono = HijrahChronology.INSTANCE;
        long testEpochDay = 12345L;
        HijrahDate date = chrono.dateEpochDay(testEpochDay);
        long epochDay = date.toEpochDay();
        if (epochDay != testEpochDay) {
            throw new AssertionError("Expected epochDay " + testEpochDay + ", but got " + epochDay);
        }
    }

    static void testDateEpochDayWithNegative() {
        HijrahChronology chrono = HijrahChronology.INSTANCE;
        long testEpochDay = -54321L;
        HijrahDate date = chrono.dateEpochDay(testEpochDay);
        long epochDay = date.toEpochDay();
        if (epochDay != testEpochDay) {
            throw new AssertionError("Expected epochDay " + testEpochDay + ", but got " + epochDay);
        }
    }
}
