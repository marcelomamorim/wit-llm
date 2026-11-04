/*
 * @test
 * @summary Test java.time.LocalTime.withMinute(int) method for valid and invalid inputs
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testWithMinuteValid();
        testWithMinuteSameValueReturnsThis();
        testWithMinuteInvalidLow();
        testWithMinuteInvalidHigh();
    }

    private static void testWithMinuteValid() {
        LocalTime time = LocalTime.of(10, 15, 30, 123);
        LocalTime changed = time.withMinute(45);
        if (changed.getMinute() != 45) {
            throw new AssertionError("Expected minute to be 45 but was " + changed.getMinute());
        }
        if (changed.getHour() != 10 || changed.getSecond() != 30 || changed.getNano() != 123) {
            throw new AssertionError("Other fields changed unexpectedly");
        }
    }

    private static void testWithMinuteSameValueReturnsThis() {
        LocalTime time = LocalTime.of(5, 20);
        LocalTime result = time.withMinute(20);
        if (result != time) {
            throw new AssertionError("Expected same instance when minute is unchanged");
        }
    }

    private static void testWithMinuteInvalidLow() {
        LocalTime time = LocalTime.of(0, 0);
        try {
            time.withMinute(-1);
            throw new AssertionError("Expected DateTimeException for minute -1");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testWithMinuteInvalidHigh() {
        LocalTime time = LocalTime.of(0, 0);
        try {
            time.withMinute(60);
            throw new AssertionError("Expected DateTimeException for minute 60");
        } catch (DateTimeException e) {
            // Expected
        }
    }
}
