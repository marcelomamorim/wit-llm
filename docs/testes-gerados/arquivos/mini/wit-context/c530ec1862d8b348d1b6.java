/*
 * @test
 * @summary Test java.time.Clock.offset static method for null checks and zero offset behavior
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Duration;

public class ClockWitupTest {
    public static void main(String[] args) {
        testNullBaseClock();
        testNullOffsetDuration();
        testZeroOffsetReturnsBaseClock();
        testNonZeroOffsetReturnsOffsetClock();
    }

    private static void testNullBaseClock() {
        try {
            Clock.offset(null, Duration.ofSeconds(1));
            throw new RuntimeException("Expected NullPointerException for null baseClock");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullOffsetDuration() {
        try {
            Clock.offset(Clock.systemUTC(), null);
            throw new RuntimeException("Expected NullPointerException for null offsetDuration");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testZeroOffsetReturnsBaseClock() {
        Clock base = Clock.systemUTC();
        Clock result = Clock.offset(base, Duration.ZERO);
        if (result != base) {
            throw new RuntimeException("Expected same baseClock instance when offsetDuration is zero");
        }
    }

    private static void testNonZeroOffsetReturnsOffsetClock() {
        Clock base = Clock.systemUTC();
        Duration offset = Duration.ofSeconds(1);
        Clock result = Clock.offset(base, offset);
        if (result == base) {
            throw new RuntimeException("Expected different Clock instance when offsetDuration is non-zero");
        }
        // Cannot assert more as OffsetClock is package-private and no public API to inspect offset
    }
}
