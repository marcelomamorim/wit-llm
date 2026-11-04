/*
 * @test
 * @summary Test java.time.LocalTime.withMinute(int) method behavior
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.DateTimeException;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testWithMinuteReturnsSameInstanceIfUnchanged();
        testWithMinuteChangesMinuteCorrectly();
        testWithMinuteRejectsInvalidMinute();
    }

    private static void testWithMinuteReturnsSameInstanceIfUnchanged() {
        LocalTime time = LocalTime.of(10, 15, 30, 123);
        LocalTime result = time.withMinute(15);
        if (result != time) {
            throw new AssertionError("Expected same instance when minute is unchanged");
        }
    }

    private static void testWithMinuteChangesMinuteCorrectly() {
        LocalTime time = LocalTime.of(10, 15, 30, 123);
        LocalTime changed = time.withMinute(45);
        if (changed.getHour() != 10) {
            throw new AssertionError("Hour should remain unchanged");
        }
        if (changed.getMinute() != 45) {
            throw new AssertionError("Minute should be changed to 45");
        }
        if (changed.getSecond() != 30) {
            throw new AssertionError("Second should remain unchanged");
        }
        if (changed.getNano() != 123) {
            throw new AssertionError("Nano should remain unchanged");
        }
    }

    private static void testWithMinuteRejectsInvalidMinute() {
        LocalTime time = LocalTime.of(10, 15);
        try {
            time.withMinute(-1);
            throw new AssertionError("Expected DateTimeException for minute -1");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            time.withMinute(60);
            throw new AssertionError("Expected DateTimeException for minute 60");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
