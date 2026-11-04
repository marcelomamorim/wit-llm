/*
 * @test
 * @summary Test java.time.Clock.tick static factory method behavior
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Duration;

public class ClockWitupTest {
    public static void main(String[] args) {
        testNullBaseClock();
        testNullTickDuration();
        testNegativeTickDuration();
        testInvalidTickDuration();
        testTickDurationOneOrLessReturnsBaseClock();
        testValidTickDurationReturnsTickClock();
    }

    private static void testNullBaseClock() {
        try {
            Clock.tick(null, Duration.ofSeconds(1));
            throw new RuntimeException("Expected NullPointerException for null baseClock");
        } catch (NullPointerException npe) {
            if (!"baseClock".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch for baseClock");
            }
        }
    }

    private static void testNullTickDuration() {
        try {
            Clock.tick(Clock.systemUTC(), null);
            throw new RuntimeException("Expected NullPointerException for null tickDuration");
        } catch (NullPointerException npe) {
            if (!"tickDuration".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch for tickDuration");
            }
        }
    }

    private static void testNegativeTickDuration() {
        try {
            Clock.tick(Clock.systemUTC(), Duration.ofNanos(-1));
            throw new RuntimeException("Expected IllegalArgumentException for negative tickDuration");
        } catch (IllegalArgumentException iae) {
            if (!"Tick duration must not be negative".equals(iae.getMessage())) {
                throw new RuntimeException("IllegalArgumentException message mismatch for negative tickDuration");
            }
        }
    }

    private static void testInvalidTickDuration() {
        // Duration that is positive but does not divide evenly into 1 second or millisecond
        Duration invalidDuration = Duration.ofNanos(1234567); // 1,234,567 ns
        try {
            Clock.tick(Clock.systemUTC(), invalidDuration);
            throw new RuntimeException("Expected IllegalArgumentException for invalid tickDuration");
        } catch (IllegalArgumentException iae) {
            if (!"Invalid tick duration".equals(iae.getMessage())) {
                throw new RuntimeException("IllegalArgumentException message mismatch for invalid tickDuration");
            }
        }
    }

    private static void testTickDurationOneOrLessReturnsBaseClock() {
        Clock base = Clock.systemUTC();
        Clock result1 = Clock.tick(base, Duration.ofNanos(1));
        if (result1 != base) {
            throw new RuntimeException("Expected same baseClock instance for tickDuration 1 ns");
        }
        Clock result0 = Clock.tick(base, Duration.ofNanos(0));
        if (result0 != base) {
            throw new RuntimeException("Expected same baseClock instance for tickDuration 0 ns");
        }
    }

    private static void testValidTickDurationReturnsTickClock() {
        Clock base = Clock.systemUTC();
        // Valid tickDuration dividing evenly into 1 second
        Duration oneSecond = Duration.ofSeconds(1);
        Clock c1 = Clock.tick(base, oneSecond);
        if (c1 == base) {
            throw new RuntimeException("Expected new Clock instance for tickDuration 1 second");
        }
        // Valid tickDuration dividing evenly into 1 millisecond
        Duration oneMilli = Duration.ofNanos(1_000_000);
        Clock c2 = Clock.tick(base, oneMilli);
        if (c2 == base) {
            throw new RuntimeException("Expected new Clock instance for tickDuration 1 millisecond");
        }
    }
}