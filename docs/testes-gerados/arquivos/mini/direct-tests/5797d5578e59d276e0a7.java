/*
 * @test
 * @summary Test java.time.Duration.withNanos(int) method
 * @run main java.time.DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoField;
import java.time.DateTimeException;

public class DurationWitupTest {
    public static void main(String[] args) {
        testWithNanosValid();
        testWithNanosInvalidLow();
        testWithNanosInvalidHigh();
    }

    private static void testWithNanosValid() {
        Duration d = Duration.ofSeconds(10, 500);
        Duration result = d.withNanos(123456789);
        if (result.getSeconds() != 10) {
            throw new AssertionError("Seconds changed unexpectedly");
        }
        if (result.getNano() != 123456789) {
            throw new AssertionError("Nano value not set correctly");
        }
    }

    private static void testWithNanosInvalidLow() {
        try {
            Duration d = Duration.ofSeconds(0, 0);
            d.withNanos(-1);
            throw new AssertionError("Expected DateTimeException for nanoOfSecond < 0");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testWithNanosInvalidHigh() {
        try {
            Duration d = Duration.ofSeconds(0, 0);
            d.withNanos(1000000000);
            throw new AssertionError("Expected DateTimeException for nanoOfSecond >= 1_000_000_000");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
