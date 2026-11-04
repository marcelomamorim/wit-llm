/*
 * @test
 * @summary Tests for java.time.LocalTime.withHour(int)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testWithHourValid();
        testWithHourSameValue();
        testWithHourInvalidLow();
        testWithHourInvalidHigh();
    }

    static void testWithHourValid() {
        LocalTime time = LocalTime.of(10, 30, 45, 123456789);
        int newHour = 15;
        LocalTime result = time.withHour(newHour);
        assert result.getHour() == newHour : "Expected hour to be " + newHour + " but was " + result.getHour();
        assert result.getMinute() == time.getMinute() : "Minutes changed unexpectedly";
        assert result.getSecond() == time.getSecond() : "Seconds changed unexpectedly";
        assert result.getNano() == time.getNano() : "Nano seconds changed unexpectedly";
    }

    static void testWithHourSameValue() {
        LocalTime time = LocalTime.of(8, 20, 30, 987654321);
        LocalTime result = time.withHour(8);
        assert result == time : "Expected same instance when hour is unchanged";
    }

    static void testWithHourInvalidLow() {
        LocalTime time = LocalTime.of(12, 0);
        try {
            time.withHour(-1);
            assert false : "Expected DateTimeException for hour -1";
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testWithHourInvalidHigh() {
        LocalTime time = LocalTime.of(12, 0);
        try {
            time.withHour(24);
            assert false : "Expected DateTimeException for hour 24";
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
