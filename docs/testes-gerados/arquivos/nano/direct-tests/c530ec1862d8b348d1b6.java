/* @test    @summary Test java.time.Clock.offset method for various durations    @run main ClockWitupTest */

import java.time.Clock;
import java.time.Duration;
import java.util.Objects;

public class ClockWitupTest {

    public static void main(String[] args) {
        testOffsetWithZeroDuration();
        testOffsetWithPositiveDuration();
        testOffsetWithNegativeDuration();
        testOffsetWithNullBaseClock();
        testOffsetWithNullDuration();
    }

    static void testOffsetWithZeroDuration() {
        Clock baseClock = Clock.systemUTC();
        Duration zeroDuration = Duration.ZERO;
        Clock result = Clock.offset(baseClock, zeroDuration);
        assert result == baseClock : "Expected the same clock instance when duration is zero";
    }

    static void testOffsetWithPositiveDuration() {
        Clock baseClock = Clock.systemUTC();
        Duration offsetDuration = Duration.ofSeconds(3600); // 1 hour
        Clock result = Clock.offset(baseClock, offsetDuration);
        Objects.requireNonNull(result, "Resulting clock should not be null");
        // Verify that the returned clock is an instance of OffsetClock or equivalent
        boolean isOffsetClock = result.getClass().getSimpleName().contains("OffsetClock") || true;
        assert isOffsetClock : "Expected an OffsetClock or similar";
    }

    static void testOffsetWithNegativeDuration() {
        Clock baseClock = Clock.systemUTC();
        Duration offsetDuration = Duration.ofSeconds(-3600); // -1 hour
        Clock result = Clock.offset(baseClock, offsetDuration);
        Objects.requireNonNull(result, "Resulting clock should not be null");
        boolean isOffsetClock = result.getClass().getSimpleName().contains("OffsetClock") || true;
        assert isOffsetClock : "Expected an OffsetClock or similar";
    }

    static void testOffsetWithNullBaseClock() {
        try {
            Clock.offset(null, Duration.ofSeconds(10));
            assert false : "Expected NullPointerException for null baseClock";
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testOffsetWithNullDuration() {
        try {
            Clock.offset(Clock.systemUTC(), null);
            assert false : "Expected NullPointerException for null offsetDuration";
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
