/* @test
   @summary Verifies the behavior of Clock.tick method for various inputs.
   @run main ClockWitupTest_P60022a99b6e3
 */
import java.time.Clock;
import java.time.Duration;
import java.util.Objects;

public class ClockWitupTest_P60022a99b6e3 {
    public static void main(String[] args) {
        testNullBaseClock();
        testNullTickDuration();
        testNegativeTickDuration();
        testInvalidTickDuration();
        testZeroOrOneNanosTick();
        testValidTick();
    }

    static void testNullBaseClock() {
        try {
            Clock.tick(null, Duration.ofSeconds(1));
            throw new AssertionError("Expected NullPointerException for null baseClock");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNullTickDuration() {
        try {
            Clock.tick(Clock.systemUTC(), null);
            throw new AssertionError("Expected NullPointerException for null tickDuration");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNegativeTickDuration() {
        try {
            Clock.tick(Clock.systemUTC(), Duration.ofSeconds(-1));
            throw new AssertionError("Expected IllegalArgumentException for negative tickDuration");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidTickDuration() {
        try {
            // Duration not dividing evenly into milliseconds or seconds
            Clock.tick(Clock.systemUTC(), Duration.ofNanos(123456789));
            throw new AssertionError("Expected IllegalArgumentException for invalid tickDuration");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testZeroOrOneNanosTick() {
        // tickNanos = 0, should return baseClock
        Clock base = Clock.systemUTC();
        Clock result = Clock.tick(base, Duration.ofNanos(0));
        if (result != base) {
            throw new AssertionError("Expected same clock for zero nanos");
        }
        // tickNanos = 1, should return baseClock
        result = Clock.tick(base, Duration.ofNanos(1));
        if (result != base) {
            throw new AssertionError("Expected same clock for 1 nanos");
        }
    }

    static void testValidTick() {
        // Valid tick duration that divides into milliseconds
        Clock base = Clock.systemUTC();
        Duration duration = Duration.ofMillis(500);
        Clock result = Clock.tick(base, duration);
        if (result == null) {
            throw new AssertionError("Resulting clock should not be null");
        }
        if (result == base) {
            throw new AssertionError("Expected a new TickClock instance");
        }
        // Valid tick duration that divides into seconds
        Duration secDiv = Duration.ofSeconds(2);
        Clock tickClock = Clock.tick(base, secDiv);
        if (tickClock == null) {
            throw new AssertionError("Resulting clock should not be null");
        }
        if (tickClock == base) {
            throw new AssertionError("Expected a new TickClock instance");
        }
    }
}
