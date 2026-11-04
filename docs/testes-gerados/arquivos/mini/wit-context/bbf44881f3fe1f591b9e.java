/*
 * @test
 * @summary Test java.time.LocalTime.withHour(int) method for valid and invalid inputs
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testWithHourValid();
        testWithHourSameValueReturnsSameInstance();
        testWithHourInvalidLow();
        testWithHourInvalidHigh();
        System.out.println("All tests passed.");
    }

    private static void testWithHourValid() {
        LocalTime time = LocalTime.of(10, 15, 30, 123);
        LocalTime changed = time.withHour(5);
        if (changed.getHour() != 5) {
            throw new AssertionError("Expected hour to be 5 but was " + changed.getHour());
        }
        if (changed.getMinute() != 15 || changed.getSecond() != 30 || changed.getNano() != 123) {
            throw new AssertionError("Other fields changed unexpectedly");
        }
    }

    private static void testWithHourSameValueReturnsSameInstance() {
        LocalTime time = LocalTime.of(8, 20);
        LocalTime result = time.withHour(8);
        if (result != time) {
            throw new AssertionError("Expected same instance when hour is unchanged");
        }
    }

    private static void testWithHourInvalidLow() {
        LocalTime time = LocalTime.of(0, 0);
        try {
            time.withHour(-1);
            throw new AssertionError("Expected DateTimeException for hour -1");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testWithHourInvalidHigh() {
        LocalTime time = LocalTime.of(0, 0);
        try {
            time.withHour(24);
            throw new AssertionError("Expected DateTimeException for hour 24");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
