/*
 * @test
 * @summary Test java.time.Duration.withNanos(int) method for valid and invalid inputs
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.DateTimeException;
import java.time.temporal.ChronoField;

public class DurationWitupTest {
    public static void main(String[] args) {
        testWithNanosValid();
        testWithNanosInvalid();
    }

    private static void testWithNanosValid() {
        // Test with valid nanos values at boundaries and typical values
        Duration d = Duration.ofSeconds(1, 0);

        // Valid nanos: 0
        Duration result = d.withNanos(0);
        if (result.getNano() != 0) {
            throw new AssertionError("Expected nanos 0, got " + result.getNano());
        }

        // Valid nanos: 999_999_999 (max valid nanos)
        result = d.withNanos(999_999_999);
        if (result.getNano() != 999_999_999) {
            throw new AssertionError("Expected nanos 999999999, got " + result.getNano());
        }

        // Valid nanos: 1
        result = d.withNanos(1);
        if (result.getNano() != 1) {
            throw new AssertionError("Expected nanos 1, got " + result.getNano());
        }

        // Valid nanos: 500_000_000
        result = d.withNanos(500_000_000);
        if (result.getNano() != 500_000_000) {
            throw new AssertionError("Expected nanos 500000000, got " + result.getNano());
        }
    }

    private static void testWithNanosInvalid() {
        Duration d = Duration.ofSeconds(1, 0);

        // Test with invalid nanos: negative value
        try {
            d.withNanos(-1);
            throw new AssertionError("Expected DateTimeException for nanos -1");
        } catch (DateTimeException e) {
            // Expected
        }

        // Test with invalid nanos: value greater than 999_999_999
        try {
            d.withNanos(1_000_000_000);
            throw new AssertionError("Expected DateTimeException for nanos 1000000000");
        } catch (DateTimeException e) {
            // Expected
        }

        // Test with invalid nanos: Integer.MIN_VALUE
        try {
            d.withNanos(Integer.MIN_VALUE);
            throw new AssertionError("Expected DateTimeException for nanos Integer.MIN_VALUE");
        } catch (DateTimeException e) {
            // Expected
        }

        // Test with invalid nanos: Integer.MAX_VALUE
        try {
            d.withNanos(Integer.MAX_VALUE);
            throw new AssertionError("Expected DateTimeException for nanos Integer.MAX_VALUE");
        } catch (DateTimeException e) {
            // Expected
        }
    }
}
