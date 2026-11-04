/*
 * @test
 * @summary Test java.time.Clock.millis() method for basic correctness
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Instant;

public class ClockWitupTest {
    public static void main(String[] args) {
        // Create a fixed clock at epoch milli 1000
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(1000L), java.time.ZoneId.of("UTC"));
        long millis = fixedClock.millis();
        if (millis != 1000L) {
            throw new AssertionError("Expected millis 1000 but got " + millis);
        }

        // Create a fixed clock at epoch milli 0
        Clock epochClock = Clock.fixed(Instant.EPOCH, java.time.ZoneId.of("UTC"));
        if (epochClock.millis() != 0L) {
            throw new AssertionError("Expected millis 0 but got " + epochClock.millis());
        }

        // Create a fixed clock at a negative epoch milli
        Clock negativeClock = Clock.fixed(Instant.ofEpochMilli(-5000L), java.time.ZoneId.of("UTC"));
        if (negativeClock.millis() != -5000L) {
            throw new AssertionError("Expected millis -5000 but got " + negativeClock.millis());
        }

        // Test system clock millis is >= 0 (cannot guarantee exact value, but should be non-negative)
        Clock systemClock = Clock.systemUTC();
        long systemMillis = systemClock.millis();
        if (systemMillis < 0) {
            throw new AssertionError("System clock millis should not be negative: " + systemMillis);
        }

        System.out.println("All tests passed.");
    }
}
