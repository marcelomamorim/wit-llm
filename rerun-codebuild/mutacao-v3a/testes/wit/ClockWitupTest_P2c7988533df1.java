/* @test    @summary Test the static method Clock.tick for various inputs and behaviors    @run main ClockWitupTest_P2c7988533df1 */

import java.time.Clock;
import java.time.Duration;

public class ClockWitupTest_P2c7988533df1 {

    public static void main(String[] args) {
        testNullBaseClock();
        testNullTickDuration();
        testNegativeTickDuration();
        testZeroOrSmallTickDuration();
        testValidTickDurationDividesMillis();
        testValidTickDurationDividesSeconds();
        testTickReturnsSameClockForSmallDuration();
    }

    static void testNullBaseClock() {
        try {
            Clock.tick(null, Duration.ofSeconds(1));
            System.err.println("testNullBaseClock failed: expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNullTickDuration() {
        try {
            Clock.tick(Clock.systemUTC(), null);
            System.err.println("testNullTickDuration failed: expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNegativeTickDuration() {
        try {
            Clock.tick(Clock.systemUTC(), Duration.ofSeconds(-1));
            System.err.println("testNegativeTickDuration failed: expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testZeroOrSmallTickDuration() {
        Clock c1 = Clock.tick(Clock.systemUTC(), Duration.ofMillis(1));
        assert c1 != null : "Returned clock should not be null for small duration";
        Clock c2 = Clock.tick(Clock.systemUTC(), Duration.ofNanos(999));
        assert c2 != null : "Returned clock should not be null for nanosecond duration";
    }

    static void testValidTickDurationDividesMillis() {
        Duration dur = Duration.ofMillis(100);
        Clock c = Clock.tick(Clock.systemUTC(), dur);
        assert c != null : "Clock should be created for valid millis duration";
    }

    static void testValidTickDurationDividesSeconds() {
        Duration dur = Duration.ofSeconds(2);
        Clock c = Clock.tick(Clock.systemUTC(), dur);
        assert c != null : "Clock should be created for valid seconds duration";
    }

    static void testTickReturnsSameClockForSmallDuration() {
        Clock base = Clock.systemUTC();
        Duration dur = Duration.ofMillis(1);
        Clock result = Clock.tick(base, dur);
        assert result == base : "For small durations, should return the same clock instance";
    }
}
