/*
 * @test
 * @summary Tests for LocalTime.ofSecondOfDay(long)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.UnsupportedTemporalTypeException;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testOfSecondOfDayZero();
        testOfSecondOfDayOneSecond();
        testOfSecondOfDayMax();
        testOfSecondOfDayNegative();
        testOfSecondOfDayExceedsMax();
    }

    static void testOfSecondOfDayZero() {
        LocalTime time = LocalTime.ofSecondOfDay(0L);
        assert time.getHour() == 0 : "Expected hour 0";
        assert time.getMinute() == 0 : "Expected minute 0";
        assert time.getSecond() == 0 : "Expected second 0";
    }

    static void testOfSecondOfDayOneSecond() {
        LocalTime time = LocalTime.ofSecondOfDay(1L);
        assert time.getHour() == 0 : "Expected hour 0";
        assert time.getMinute() == 0 : "Expected minute 0";
        assert time.getSecond() == 1 : "Expected second 1";
    }

    static void testOfSecondOfDayMax() {
        long maxSeconds = 86399L; // 23:59:59
        LocalTime time = LocalTime.ofSecondOfDay(maxSeconds);
        assert time.getHour() == 23 : "Expected hour 23";
        assert time.getMinute() == 59 : "Expected minute 59";
        assert time.getSecond() == 59 : "Expected second 59";
    }

    static void testOfSecondOfDayNegative() {
        try {
            LocalTime.ofSecondOfDay(-1L);
            assert false : "Expected exception for negative seconds";
        } catch (UnsupportedTemporalTypeException e) {
            // Expected exception
        }
    }

    static void testOfSecondOfDayExceedsMax() {
        long exceedsMax = 86400L; // 24:00:00, invalid
        try {
            LocalTime.ofSecondOfDay(exceedsMax);
            assert false : "Expected exception for seconds exceeding max";
        } catch (UnsupportedTemporalTypeException e) {
            // Expected exception
        }
    }
}
