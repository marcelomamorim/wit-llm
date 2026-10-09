/* @test    @summary Test Clock.tick method for valid and invalid inputs    @run main ClockWitupTest_Pfa45f27a64cf */

import java.time.Clock;
import java.time.Duration;

public class ClockWitupTest_Pfa45f27a64cf {

    public static void main(String[] args) {
        testTickWithValidDurations();
        testTickWithNegativeDuration();
        testTickWithInvalidDuration();
        testTickWithMinimalDuration();
        testTickWithLargeDuration();
    }

    static void testTickWithValidDurations() {
        Clock baseClock = Clock.systemUTC();
        Duration[] validDurations = {
            Duration.ofMillis(100),
            Duration.ofSeconds(1),
            Duration.ofNanos(500_000_000),
            Duration.ofMillis(1000),
            Duration.ofSeconds(2)
        };
        for (Duration d : validDurations) {
            try {
                Clock c = Clock.tick(baseClock, d);
                if (c == null) {
                    throw new AssertionError("tick returned null for duration: " + d);
                }
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception for duration: " + d, e);
            }
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
        // Duration that does not divide evenly into 1 second or no fraction of millisecond
        Duration invalidDuration = Duration.ofNanos(333_333_333);
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
            Clock c = Clock.tick(baseClock, minimalDuration);
            if (c == null) {
                throw new AssertionError("tick returned null for minimal duration");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for minimal duration", e);
        }
    }

    static void testTickWithLargeDuration() {
        Clock baseClock = Clock.systemUTC();
        Duration largeDuration = Duration.ofSeconds(1000000);
        try {
            Clock c = Clock.tick(baseClock, largeDuration);
            if (c == null) {
                throw new AssertionError("tick returned null for large duration");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for large duration", e);
        }
    }
}
