/*
 * @test
 * @summary Witup-generated checks for Clock.tick(Clock, Duration) argument validation and observable results
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.Instant;

public class ClockWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    private static Clock baseClock() {
        return new Clock() {
            @Override
            public ZoneId getZone() {
                return ZoneOffset.UTC;
            }

            @Override
            public Clock withZone(ZoneId zone) {
                return this;
            }

            @Override
            public Instant instant() {
                return Instant.ofEpochSecond(1000L);
            }
        };
    }

    private static void testNullBaseClock() {
        boolean thrown = false;
        try {
            Clock.tick(null, Duration.ofMillis(1));
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null baseClock must throw NullPointerException");
    }

    private static void testNullTickDuration() {
        boolean thrown = false;
        try {
            Clock.tick(baseClock(), null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null tickDuration must throw NullPointerException");
    }

    private static void testNegativeDuration() {
        boolean thrown = false;
        try {
            Clock.tick(baseClock(), Duration.ofSeconds(-1));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "negative tickDuration must throw IllegalArgumentException");
    }

    private static void testInvalidTickDuration() {
        boolean thrown = false;
        try {
            Clock.tick(baseClock(), Duration.ofNanos(3));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "non-divisor nanosecond tick must throw IllegalArgumentException");
    }

    private static void testOverflowingDuration() {
        boolean thrown = false;
        try {
            Clock.tick(baseClock(), Duration.ofSeconds(Long.MAX_VALUE));
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "duration overflowing nanoseconds must throw ArithmeticException");
    }

    private static void testZeroDurationReturnsBase() {
        Clock base = baseClock();
        Clock result = Clock.tick(base, Duration.ZERO);
        check(result == base, "zero tick duration must return baseClock itself");
    }

    private static void testOneNanoReturnsBase() {
        Clock base = baseClock();
        Clock result = Clock.tick(base, Duration.ofNanos(1));
        check(result == base, "1ns tick duration must return baseClock itself");
    }

    private static void testMillisReturnsTickClock() {
        Clock base = baseClock();
        Clock result = Clock.tick(base, Duration.ofMillis(500));
        check(result != null, "valid tick duration must not return null");
        check(result != base, "500ms tick must return a new clock");
        check(result.getZone().equals(ZoneOffset.UTC), "tick clock must keep base zone");
    }

    public static void main(String[] args) {
        testNullBaseClock();
        testNullTickDuration();
        testNegativeDuration();
        testInvalidTickDuration();
        testOverflowingDuration();
        testZeroDurationReturnsBase();
        testOneNanoReturnsBase();
        testMillisReturnsTickClock();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
