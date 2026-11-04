/*
 * @test
 * @summary Test for LocalTime.withMinute(int)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.UnsupportedTemporalTypeException;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testWithMinuteValid();
        testWithMinuteEqual();
        testWithMinuteInvalidLow();
        testWithMinuteInvalidHigh();
    }

    static void testWithMinuteValid() {
        LocalTime time = LocalTime.of(10, 30, 45, 500000000);
        int newMinute = 15;
        LocalTime result = time.withMinute(newMinute);
        assert result.getMinute() == newMinute : "Expected minute to be " + newMinute + " but was " + result.getMinute();
        assert result.getHour() == time.getHour() : "Hour changed unexpectedly";
        assert result.getSecond() == time.getSecond() : "Second changed unexpectedly";
        assert result.getNano() == time.getNano() : "Nano changed unexpectedly";
    }

    static void testWithMinuteEqual() {
        LocalTime time = LocalTime.of(12, 20, 30, 400000000);
        LocalTime result = time.withMinute(time.getMinute());
        assert result == time : "Should return same instance when minute is unchanged";
    }

    static void testWithMinuteInvalidLow() {
        try {
            LocalTime.of(10, 0).withMinute(-1);
            assert false : "Expected exception for minute -1";
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testWithMinuteInvalidHigh() {
        try {
            LocalTime.of(10, 0).withMinute(60);
            assert false : "Expected exception for minute 60";
        } catch (DateTimeException e) {
            // expected
        }
    }
}
