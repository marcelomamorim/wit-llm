/*
 * @test
 * @summary Test java.time.Clock.millis() method behavior
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

public class ClockWitupTest {
    public static void main(String[] args) {
        testMillisWithZeroOffset();
        testMillisWithPositiveOffset();
        testMillisWithNegativeOffset();
        testMillisOverflow();
    }

    private static void testMillisWithZeroOffset() {
        Clock base = Clock.fixed(Instant.ofEpochMilli(1000L), ZoneId.of("UTC"));
        Clock offsetClock = new OffsetClock(base, Duration.ZERO);
        long result = offsetClock.millis();
        if (result != 1000L) {
            throw new AssertionError("Expected 1000 but got " + result);
        }
    }

    private static void testMillisWithPositiveOffset() {
        Clock base = Clock.fixed(Instant.ofEpochMilli(1000L), ZoneId.of("UTC"));
        Clock offsetClock = new OffsetClock(base, Duration.ofMillis(500));
        long result = offsetClock.millis();
        if (result != 1500L) {
            throw new AssertionError("Expected 1500 but got " + result);
        }
    }

    private static void testMillisWithNegativeOffset() {
        Clock base = Clock.fixed(Instant.ofEpochMilli(1000L), ZoneId.of("UTC"));
        Clock offsetClock = new OffsetClock(base, Duration.ofMillis(-500));
        long result = offsetClock.millis();
        if (result != 500L) {
            throw new AssertionError("Expected 500 but got " + result);
        }
    }

    private static void testMillisOverflow() {
        Clock base = Clock.fixed(Instant.ofEpochMilli(Long.MAX_VALUE), ZoneId.of("UTC"));
        Clock offsetClock = new OffsetClock(base, Duration.ofMillis(1));
        try {
            offsetClock.millis();
            throw new AssertionError("Expected ArithmeticException due to overflow");
        } catch (ArithmeticException expected) {
            // expected
        }
    }

    // Helper Clock implementation to simulate baseClock and offset
    private static class OffsetClock extends Clock {
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
            return Math.addExact(baseClock.millis(), offset.toMillis());
        }
    }
}
