/*
 * @test
 * @summary Test java.time.Clock.offset static factory method
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

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
        } catch (NullPointerException npe) {
            if (!"baseClock".equals(npe.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + npe.getMessage());
            }
        }
    }

    private static void testNullOffsetDuration() {
        try {
            Clock.offset(Clock.systemUTC(), null);
            throw new RuntimeException("Expected NullPointerException for null offsetDuration");
        } catch (NullPointerException npe) {
            if (!"offsetDuration".equals(npe.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + npe.getMessage());
            }
        }
    }

    private static void testZeroOffsetReturnsBaseClock() {
        Clock base = Clock.systemUTC();
        Clock result = Clock.offset(base, Duration.ZERO);
        if (result != base) {
            throw new RuntimeException("Expected the same baseClock instance when offsetDuration is zero");
        }
    }

    private static void testNonZeroOffsetReturnsOffsetClock() {
        Clock base = Clock.systemUTC();
        Duration offset = Duration.ofSeconds(10);
        Clock offsetClock = Clock.offset(base, offset);
        if (offsetClock == base) {
            throw new RuntimeException("Expected a new Clock instance different from baseClock");
        }
        // Check that the offset is applied by comparing instants
        Instant baseInstant = base.instant();
        Instant offsetInstant = offsetClock.instant();
        long diff = offsetInstant.getEpochSecond() - baseInstant.getEpochSecond();
        if (diff != offset.getSeconds()) {
            throw new RuntimeException("OffsetClock instant does not reflect the offset duration");
        }
    }
}
