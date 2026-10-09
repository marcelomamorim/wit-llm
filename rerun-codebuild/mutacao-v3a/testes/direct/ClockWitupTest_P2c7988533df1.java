/* @test    @summary Test Clock.tick with various durations and inputs    @run main ClockWitupTest_P2c7988533df1 */

import java.time.Clock;
import java.time.Duration;

public class ClockWitupTest_P2c7988533df1 {

    public static void main(String[] args) {
        testTickWithValidDuration();
        testTickWithNegativeDuration();
        testTickWithZeroDuration();
        testTickWithInvalidDuration();
    }

    public static void testTickWithValidDuration() {
        Clock baseClock = Clock.systemUTC();
        Duration validDuration1 = Duration.ofMillis(10);
        Duration validDuration2 = Duration.ofSeconds(1);
        try {
            Clock clock1 = Clock.tick(baseClock, validDuration1);
            assert clock1 != null : "Clock.tick should return a Clock instance for valid duration in milliseconds";
            Clock clock2 = Clock.tick(baseClock, validDuration2);
            assert clock2 != null : "Clock.tick should return a Clock instance for valid duration in seconds";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testTickWithValidDuration", e);
        }
    }

    public static void testTickWithNegativeDuration() {
        Clock baseClock = Clock.systemUTC();
        Duration negativeDuration = Duration.ofMillis(-5);
        try {
            Clock.tick(baseClock, negativeDuration);
            throw new AssertionError("Expected IllegalArgumentException for negative duration");
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Tick duration must not be negative") : "Exception message mismatch";
        }
    }

    public static void testTickWithZeroDuration() {
        Clock baseClock = Clock.systemUTC();
        Duration zeroDuration = Duration.ofMillis(0);
        Clock resultClock = Clock.tick(baseClock, zeroDuration);
        assert resultClock == baseClock : "Tick with zero duration should return the base clock";
    }

    public static void testTickWithInvalidDuration() {
        Clock baseClock = Clock.systemUTC();
        // Duration that does not divide evenly into 1 second or milliseconds
        Duration invalidDuration = Duration.ofNanos(1500000); // 1.5 milliseconds
        try {
            Clock.tick(baseClock, invalidDuration);
            throw new AssertionError("Expected IllegalArgumentException for invalid tick duration");
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Invalid tick duration") : "Exception message mismatch";
        }
    }
}
