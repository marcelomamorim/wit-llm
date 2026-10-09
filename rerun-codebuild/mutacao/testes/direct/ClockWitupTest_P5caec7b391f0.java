/* @test    @summary Test Clock.tick method for valid and invalid durations    @run main ClockWitupTest_P5caec7b391f0 */

import java.time.Clock;
import java.time.Duration;

public class ClockWitupTest_P5caec7b391f0 {

    public static void main(String[] args) {
        testTickWithValidDurations();
        testTickWithNegativeDuration();
        testTickWithInvalidDuration();
        testTickWithMinimalDuration();
    }

    static void testTickWithValidDurations() {
        Clock baseClock = Clock.systemUTC();
        Duration validDuration1 = Duration.ofMillis(100);
        Duration validDuration2 = Duration.ofSeconds(1);
        Duration validDuration3 = Duration.ofNanos(500_000_000); // 0.5 seconds
        try {
            Clock clock1 = Clock.tick(baseClock, validDuration1);
            Clock clock2 = Clock.tick(baseClock, validDuration2);
            Clock clock3 = Clock.tick(baseClock, validDuration3);
            if (clock1 == null || clock2 == null || clock3 == null) {
                throw new AssertionError("Clock.tick returned null for valid durations");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid durations: " + e);
        }
    }

    static void testTickWithNegativeDuration() {
        Clock baseClock = Clock.systemUTC();
        Duration negativeDuration = Duration.ofMillis(-100);
        try {
            Clock.tick(baseClock, negativeDuration);
            throw new AssertionError("Expected IllegalArgumentException for negative duration");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testTickWithInvalidDuration() {
        Clock baseClock = Clock.systemUTC();
        Duration invalidDuration = Duration.ofNanos(3); // not divisible by 1000000 or 1_000_000_000
        try {
            Clock.tick(baseClock, invalidDuration);
            throw new AssertionError("Expected IllegalArgumentException for invalid duration");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testTickWithMinimalDuration() {
        Clock baseClock = Clock.systemUTC();
        Duration minimalDuration = Duration.ofNanos(1);
        try {
            Clock resultClock = Clock.tick(baseClock, minimalDuration);
            if (resultClock != baseClock) {
                throw new AssertionError("Expected returned clock to be the same as baseClock for minimal duration");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for minimal duration: " + e);
        }
    }
}
