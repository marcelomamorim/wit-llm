/*
 * @test
 * @summary Test java.time.LocalTime.withSecond(int) method behavior
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.DateTimeException;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testWithSecondReturnsSameInstanceIfUnchanged();
        testWithSecondChangesSecondCorrectly();
        testWithSecondRejectsInvalidLowValue();
        testWithSecondRejectsInvalidHighValue();
    }

    private static void testWithSecondReturnsSameInstanceIfUnchanged() {
        LocalTime time = LocalTime.of(10, 15, 30, 123);
        LocalTime result = time.withSecond(30);
        if (result != time) {
            throw new AssertionError("Expected same instance when second unchanged");
        }
    }

    private static void testWithSecondChangesSecondCorrectly() {
        LocalTime time = LocalTime.of(10, 15, 30, 123);
        LocalTime changed = time.withSecond(45);
        if (changed.getHour() != 10 || changed.getMinute() != 15 || changed.getSecond() != 45 || changed.getNano() != 123) {
            throw new AssertionError("withSecond did not set second correctly");
        }
        if (changed == time) {
            throw new AssertionError("Expected new instance when second changed");
        }
    }

    private static void testWithSecondRejectsInvalidLowValue() {
        LocalTime time = LocalTime.of(0, 0, 0);
        try {
            time.withSecond(-1);
            throw new AssertionError("Expected DateTimeException for second < 0");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testWithSecondRejectsInvalidHighValue() {
        LocalTime time = LocalTime.of(0, 0, 0);
        try {
            time.withSecond(60);
            throw new AssertionError("Expected DateTimeException for second > 59");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
