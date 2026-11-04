/*
 * @test
 * @summary Test java.time.LocalTime.withNano(int) method behavior
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ValueRange;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testWithNanoReturnsSameInstanceIfUnchanged();
        testWithNanoReturnsNewInstanceIfChanged();
        testWithNanoRejectsInvalidNegative();
        testWithNanoRejectsInvalidTooLarge();
    }

    private static void testWithNanoReturnsSameInstanceIfUnchanged() {
        LocalTime time = LocalTime.of(10, 15, 30, 123456789);
        LocalTime result = time.withNano(123456789);
        if (result != time) {
            throw new AssertionError("Expected same instance when nano unchanged");
        }
    }

    private static void testWithNanoReturnsNewInstanceIfChanged() {
        LocalTime time = LocalTime.of(10, 15, 30, 123456789);
        LocalTime result = time.withNano(987654321);
        if (result == time) {
            throw new AssertionError("Expected different instance when nano changed");
        }
        if (result.getHour() != 10 || result.getMinute() != 15 || result.getSecond() != 30 || result.getNano() != 987654321) {
            throw new AssertionError("Resulting LocalTime has incorrect fields");
        }
    }

    private static void testWithNanoRejectsInvalidNegative() {
        LocalTime time = LocalTime.of(0, 0);
        try {
            time.withNano(-1);
            throw new AssertionError("Expected exception for negative nano value");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testWithNanoRejectsInvalidTooLarge() {
        LocalTime time = LocalTime.of(0, 0);
        try {
            time.withNano(1000000000); // 1_000_000_000 is invalid nano
            throw new AssertionError("Expected exception for too large nano value");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
