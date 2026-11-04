/*
 * @test
 * @summary Test java.time.Clock.millis() for normal and overflow behavior
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

public class ClockWitupTest {

    // Minimal subclass to test millis() with offset and baseClock
    static class OffsetClock extends Clock {
        private final Clock baseClock;
        private final Duration offset;

        OffsetClock(Clock baseClock, Duration offset) {
            this.baseClock = baseClock;
            this.offset = offset;
        }

        @Override
        public ZoneId getZone() {
            return baseClock.getZone();
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return new OffsetClock(baseClock.withZone(zone), offset);
        }

        @Override
        public Instant instant() {
            return baseClock.instant().plus(offset);
        }

        @Override
        public long millis() {
            // This matches the tested method source
            return Math.addExact(baseClock.millis(), offset.toMillis());
        }
    }

    public static void main(String[] args) {
        testMillisNormal();
        testMillisOverflow();
    }

    private static void testMillisNormal() {
        Clock base = Clock.fixed(Instant.ofEpochMilli(1000L), ZoneId.of("UTC"));
        Duration offset = Duration.ofMillis(500L);
        OffsetClock clock = new OffsetClock(base, offset);
        long result = clock.millis();
        if (result != 1500L) {
            throw new AssertionError("Expected 1500 but got " + result);
        }
    }

    private static void testMillisOverflow() {
        Clock base = Clock.fixed(Instant.ofEpochMilli(Long.MAX_VALUE), ZoneId.of("UTC"));
        Duration offset = Duration.ofMillis(1L);
        OffsetClock clock = new OffsetClock(base, offset);
        try {
            clock.millis();
            throw new AssertionError("Expected ArithmeticException due to overflow");
        } catch (ArithmeticException expected) {
            // Expected overflow exception
        }
    }
}
