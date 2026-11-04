/*
 * @test
 * @summary Test java.time.Clock.tick static method for argument validation and behavior
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
        } catch (NullPointerException expected) {
        }
    }

    private static void testNullTickDuration() {
        try {
            Clock.tick(Clock.systemUTC(), null);
            throw new RuntimeException("Expected NullPointerException for null tickDuration");
        } catch (NullPointerException expected) {
        }
    }

    private static void testNegativeTickDuration() {
        try {
            Clock.tick(Clock.systemUTC(), Duration.ofSeconds(-1));
            throw new RuntimeException("Expected IllegalArgumentException for negative tickDuration");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testInvalidTickDuration() {
        // tickDuration that is positive but does not divide evenly into 1 second or millisecond
        Duration invalidDuration = Duration.ofNanos(7); // 7 ns does not divide 1_000_000_000 or 1_000_000 evenly
        try {
            Clock.tick(Clock.systemUTC(), invalidDuration);
            throw new RuntimeException("Expected IllegalArgumentException for invalid tickDuration");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testTickDurationOneOrLessReturnsBaseClock() {
        Clock base = Clock.systemUTC();
        Clock result1 = Clock.tick(base, Duration.ofNanos(1));
        if (result1 != base) {
            throw new RuntimeException("Expected same baseClock instance when tickDuration <= 1");
        }
        Clock result0 = Clock.tick(base, Duration.ofNanos(0));
        if (result0 != base) {
            throw new RuntimeException("Expected same baseClock instance when tickDuration <= 1");
        }
    }

    private static void testValidTickDurationReturnsTickClock() {
        Clock base = Clock.systemUTC();
        // Valid tickDuration dividing 1 second exactly
        Duration oneSecond = Duration.ofSeconds(1);
        Clock c1 = Clock.tick(base, oneSecond);
        if (c1 == base) {
            throw new RuntimeException("Expected new Clock instance for valid tickDuration > 1");
        }
        // Valid tickDuration dividing 1 millisecond exactly
        Duration oneMilli = Duration.ofMillis(1);
        Clock c2 = Clock.tick(base, oneMilli);
        if (c2 == base) {
            throw new RuntimeException("Expected new Clock instance for valid tickDuration > 1");
        }
    }
}
