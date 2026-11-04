/*
 * @test
 * @summary Test LocalTime.ofNanoOfDay(long) for valid and boundary values
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testOfNanoOfDayZero();
        testOfNanoOfDayMidnightMinusOne();
        testOfNanoOfDayMidnight();
        testOfNanoOfDayMax();
    }

    private static void testOfNanoOfDayZero() {
        LocalTime t = LocalTime.ofNanoOfDay(0L);
        if (t.getHour() != 0 || t.getMinute() != 0 || t.getSecond() != 0 || t.getNano() != 0) {
            throw new AssertionError("Expected midnight for nanoOfDay=0");
        }
    }

    private static void testOfNanoOfDayMidnightMinusOne() {
        // One nano before midnight (end of previous day)
        long nanoOfDay = ChronoField.NANO_OF_DAY.range().getMaximum() - 1;
        LocalTime t = LocalTime.ofNanoOfDay(nanoOfDay);
        if (t.getHour() != 23 || t.getMinute() != 59 || t.getSecond() != 59 || t.getNano() != 999_999_999) {
            throw new AssertionError("Expected 23:59:59.999999999 for nanoOfDay=max-1");
        }
    }

    private static void testOfNanoOfDayMidnight() {
        // Maximum valid nanoOfDay is 24*60*60*1_000_000_000 - 1
        long maxNano = ChronoField.NANO_OF_DAY.range().getMaximum();
        LocalTime t = LocalTime.ofNanoOfDay(maxNano);
        if (t.getHour() != 23 || t.getMinute() != 59 || t.getSecond() != 59 || t.getNano() != 999_999_999) {
            throw new AssertionError("Expected 23:59:59.999999999 for nanoOfDay=max");
        }
    }

    private static void testOfNanoOfDayMax() {
        // Test that passing max+1 throws DateTimeException
        long maxNano = ChronoField.NANO_OF_DAY.range().getMaximum();
        try {
            LocalTime.ofNanoOfDay(maxNano + 1);
            throw new AssertionError("Expected exception for nanoOfDay > max");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
