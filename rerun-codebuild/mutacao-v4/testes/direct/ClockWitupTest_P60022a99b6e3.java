/* @test
   @summary Verifies the behavior of Clock.tick method with valid and invalid inputs.
   @run main ClockWitupTest_P60022a99b6e3
 */
import java.time.Clock;
import java.time.Duration;
public class ClockWitupTest_P60022a99b6e3 {
    public static void main(String[] args) {
        testTickWithValidDuration();
        testTickWithNegativeDuration();
        testTickWithZeroDuration();
        testTickWithInvalidDuration();
    }

    static void testTickWithValidDuration() {
        Clock baseClock = Clock.systemUTC();
        Duration validDuration = Duration.ofMillis(100);
        Clock resultClock = Clock.tick(baseClock, validDuration);
        if (resultClock == null) {
            throw new AssertionError("tick with valid duration returned null");
        }
        if (resultClock == baseClock) {
            throw new AssertionError("Expected a new Clock instance, got the same");
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

    static void testTickWithZeroDuration() {
        Clock baseClock = Clock.systemUTC();
        Duration zeroDuration = Duration.ofMillis(0);
        Clock resultClock = Clock.tick(baseClock, zeroDuration);
        if (resultClock != baseClock) {
            throw new AssertionError("tick with zero duration should return the base clock");
        }
    }

    static void testTickWithInvalidDuration() {
        Clock baseClock = Clock.systemUTC();
        // Duration that does not divide evenly into milliseconds or seconds
        Duration invalidDuration = Duration.ofNanos(123456789);
        try {
            Clock.tick(baseClock, invalidDuration);
            throw new AssertionError("Expected IllegalArgumentException for invalid duration");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
