/* @test    @summary Test for Clock.tick method behavior and exception paths    @run main ClockWitupTest_Pfa45f27a64cf */

import java.time.Clock;
import java.time.Duration;

public class ClockWitupTest_Pfa45f27a64cf {

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
            // Duration with nanoseconds not dividing into 1ms or 1s
            Clock.tick(Clock.systemUTC(), Duration.ofNanos(123456789));
            throw new AssertionError("Expected IllegalArgumentException for invalid tickDuration");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testZeroOrOneNanosTick() {
        // Zero nanos should return the same clock
        Clock clock = Clock.systemUTC();
        Clock result = Clock.tick(clock, Duration.ofNanos(0));
        if (result != clock) {
            throw new AssertionError("Expected same clock for zero nanos tick");
        }
        // One nanosecond should return the same clock
        result = Clock.tick(clock, Duration.ofNanos(1));
        if (result != clock) {
            throw new AssertionError("Expected same clock for 1 nanosecond tick");
        }
    }

    static void testValidTick() {
        Clock baseClock = Clock.systemUTC();
        Duration duration = Duration.ofSeconds(2);
        Clock tickClock = Clock.tick(baseClock, duration);
        if (tickClock == null) {
            throw new AssertionError("tickClock should not be null for valid input");
        }
        if (tickClock == baseClock) {
            // It can be same if tickNanos <= 1, but here duration is 2s
            throw new AssertionError("Expected a different clock instance for valid tick");
        }
    }
}
