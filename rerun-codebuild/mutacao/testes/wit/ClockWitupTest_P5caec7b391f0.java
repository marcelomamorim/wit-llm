/* @test    @summary Test the static method Clock.tick for various scenarios    @run main ClockWitupTest_P5caec7b391f0 */

import java.time.Clock;
import java.time.Duration;

public class ClockWitupTest_P5caec7b391f0 {

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
            System.out.println("FAIL: Expected NullPointerException for null baseClock");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException for null baseClock");
        }
    }

    static void testNullTickDuration() {
        try {
            Clock.tick(Clock.systemUTC(), null);
            System.out.println("FAIL: Expected NullPointerException for null tickDuration");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException for null tickDuration");
        }
    }

    static void testNegativeTickDuration() {
        try {
            Clock.tick(Clock.systemUTC(), Duration.ofSeconds(-1));
            System.out.println("FAIL: Expected IllegalArgumentException for negative tickDuration");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException for negative tickDuration");
        }
    }

    static void testInvalidTickDuration() {
        try {
            Duration invalidDuration = Duration.ofNanos(1500000); // 1.5 milliseconds
            Clock.tick(Clock.systemUTC(), invalidDuration);
            System.out.println("FAIL: Expected IllegalArgumentException for invalid tickDuration");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException for invalid tickDuration");
        }
    }

    static void testZeroOrOneNanosTick() {
        try {
            Clock clockZero = Clock.tick(Clock.systemUTC(), Duration.ofNanos(1));
            if (clockZero == null) {
                System.out.println("FAIL: tick returned null for 1 nanosecond duration");
            } else {
                System.out.println("PASS: tick returned clock for 1 nanosecond duration");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception for 1 nanosecond duration: " + e);
        }
        try {
            Clock clockZero = Clock.tick(Clock.systemUTC(), Duration.ofNanos(0));
            if (clockZero != null) {
                System.out.println("PASS: tick returned clock for 0 nanoseconds (no change)");
            } else {
                System.out.println("FAIL: tick returned null for 0 nanoseconds");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception for 0 nanoseconds: " + e);
        }
    }

    static void testValidTick() {
        try {
            Clock baseClock = Clock.systemUTC();
            Duration duration = Duration.ofSeconds(10);
            Clock tickClock = Clock.tick(baseClock, duration);
            if (tickClock != null) {
                System.out.println("PASS: Valid tick returned a Clock instance");
            } else {
                System.out.println("FAIL: Valid tick returned null");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception during valid tick: " + e);
        }
    }
}
