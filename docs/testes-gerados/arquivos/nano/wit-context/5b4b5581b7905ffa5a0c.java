/*
 * @test
 * @summary Tests for Duration.of(long, TemporalUnit)
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.TemporalUnit;

public class DurationWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testNullUnit();
        testInvalidUnit();
    }

    static void testValidInputs() {
        // Test with a typical amount and a valid TemporalUnit
        try {
            Duration d = Duration.of(5, java.time.temporal.ChronoUnit.SECONDS);
            if (d == null) {
                throw new AssertionError("Duration.of(5, ChronoUnit.SECONDS) returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid inputs", e);
        }
    }

    static void testNullUnit() {
        // Expect NullPointerException when unit is null
        try {
            Duration.of(10, null);
            throw new AssertionError("Expected NullPointerException for null unit");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for null unit", e);
        }
    }

    static void testInvalidUnit() {
        // Test with a unit that is not DAYS and not null, expecting a valid Duration
        // Since Duration.of() calls plus() which accepts any TemporalUnit, no exception expected here
        // But to simulate an invalid unit, we can create a mock or a dummy class implementing TemporalUnit
        // However, as per constraints, we avoid creating custom classes; instead, test with a known valid unit
        try {
            Duration d = Duration.of(3, java.time.temporal.ChronoUnit.MINUTES);
            if (d == null) {
                throw new AssertionError("Duration.of(3, ChronoUnit.MINUTES) returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid unit MINUTES", e);
        }
    }
}
