/*
 * @test
 * @summary Tests for LocalTime.ofNanoOfDay(long nanoOfDay)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testOfNanoOfDayZero();
        testOfNanoOfDayMax();
        testOfNanoOfDayMid();
        testOfNanoOfDayInvalidNegative();
        testOfNanoOfDayInvalidTooLarge();
    }

    static void testOfNanoOfDayZero() {
        LocalTime time = LocalTime.ofNanoOfDay(0L);
        assert time.getLong(ChronoField.NANO_OF_DAY) == 0L : "Expected nano of day 0";
        assert time.getHour() == 0 : "Expected hour 0";
        assert time.getMinute() == 0 : "Expected minute 0";
        assert time.getSecond() == 0 : "Expected second 0";
        assert time.getNano() == 0 : "Expected nano 0";
    }

    static void testOfNanoOfDayMax() {
        long maxNano = (long) (LocalTime.NANOS_PER_DAY - 1);
        LocalTime time = LocalTime.ofNanoOfDay(maxNano);
        assert time.getLong(ChronoField.NANO_OF_DAY) == maxNano : "Expected max nano of day";
        int totalSeconds = (int) (maxNano / LocalTime.NANOS_PER_SECOND);
        int expectedHour = totalSeconds / 3600;
        int expectedMinute = (totalSeconds % 3600) / 60;
        int expectedSecond = totalSeconds % 60;
        int expectedNano = (int) (maxNano % LocalTime.NANOS_PER_SECOND);
        assert time.getHour() == expectedHour : "Expected hour " + expectedHour;
        assert time.getMinute() == expectedMinute : "Expected minute " + expectedMinute;
        assert time.getSecond() == expectedSecond : "Expected second " + expectedSecond;
        assert time.getNano() == expectedNano : "Expected nano " + expectedNano;
    }

    static void testOfNanoOfDayMid() {
        long midNano = LocalTime.NANOS_PER_DAY / 2;
        LocalTime time = LocalTime.ofNanoOfDay(midNano);
        assert time.getLong(ChronoField.NANO_OF_DAY) == midNano : "Expected mid nano of day";
        int totalSeconds = (int) (midNano / LocalTime.NANOS_PER_SECOND);
        int expectedHour = totalSeconds / 3600;
        int expectedMinute = (totalSeconds % 3600) / 60;
        int expectedSecond = totalSeconds % 60;
        int expectedNano = (int) (midNano % LocalTime.NANOS_PER_SECOND);
        assert time.getHour() == expectedHour : "Expected hour " + expectedHour;
        assert time.getMinute() == expectedMinute : "Expected minute " + expectedMinute;
        assert time.getSecond() == expectedSecond : "Expected second " + expectedSecond;
        assert time.getNano() == expectedNano : "Expected nano " + expectedNano;
    }

    static void testOfNanoOfDayInvalidNegative() {
        try {
            LocalTime.ofNanoOfDay(-1L);
            assert false : "Expected exception for negative nanoOfDay";
        } catch (Exception e) {
            // Expected
        }
    }

    static void testOfNanoOfDayInvalidTooLarge() {
        long tooLarge = (long) LocalTime.NANOS_PER_DAY;
        try {
            LocalTime.ofNanoOfDay(tooLarge);
            assert false : "Expected exception for too large nanoOfDay";
        } catch (Exception e) {
            // Expected
        }
    }
}
