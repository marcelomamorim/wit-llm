/*
 * @test
 * @summary Test java.time.LocalTime.withHour(int) method behavior
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testWithHourReturnsSameInstanceIfUnchanged();
        testWithHourChangesHourCorrectly();
        testWithHourRejectsInvalidLowHour();
        testWithHourRejectsInvalidHighHour();
    }

    private static void testWithHourReturnsSameInstanceIfUnchanged() {
        LocalTime time = LocalTime.of(10, 20, 30, 40);
        LocalTime result = time.withHour(10);
        if (result != time) {
            throw new AssertionError("withHour should return same instance if hour unchanged");
        }
    }

    private static void testWithHourChangesHourCorrectly() {
        LocalTime time = LocalTime.of(10, 20, 30, 40);
        LocalTime changed = time.withHour(15);
        if (changed.getHour() != 15) {
            throw new AssertionError("withHour did not set hour correctly");
        }
        if (changed.getMinute() != 20 || changed.getSecond() != 30 || changed.getNano() != 40) {
            throw new AssertionError("withHour changed other fields unexpectedly");
        }
    }

    private static void testWithHourRejectsInvalidLowHour() {
        LocalTime time = LocalTime.of(10, 20);
        try {
            time.withHour(-1);
            throw new AssertionError("withHour should throw exception for hour < 0");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testWithHourRejectsInvalidHighHour() {
        LocalTime time = LocalTime.of(10, 20);
        try {
            time.withHour(24);
            throw new AssertionError("withHour should throw exception for hour >= 24");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
