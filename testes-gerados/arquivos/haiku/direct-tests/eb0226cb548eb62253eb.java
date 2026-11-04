/*
 * @test
 * @summary Clock.millis() returns instant().toEpochMilli() for representative instants
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class ClockWitupTest {

    static final class FixedClock extends Clock {
        private final Instant instant;
        private final ZoneId zone;
        int instantCalls;

        FixedClock(Instant instant, ZoneId zone) {
            this.instant = instant;
            this.zone = zone;
        }

        @Override
        public ZoneId getZone() {
            return zone;
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return new FixedClock(instant, zone);
        }

        @Override
        public Instant instant() {
            instantCalls++;
            return instant;
        }
    }

    static void check(long expected, Instant instant) {
        FixedClock clock = new FixedClock(instant, ZoneId.of("UTC"));
        long actual = clock.millis();
        if (actual != expected) {
            throw new RuntimeException("Expected " + expected + " but got " + actual
                    + " for instant " + instant);
        }
        if (clock.instantCalls != 1) {
            throw new RuntimeException("millis() must call instant() exactly once, calls="
                    + clock.instantCalls);
        }
    }

    public static void main(String[] args) {
        check(0L, Instant.EPOCH);
        check(123456789L, Instant.ofEpochMilli(123456789L));
        check(1999L, Instant.ofEpochSecond(1, 999_999_999L));
        check(-500L, Instant.ofEpochSecond(-1, 500_000_000L));
        check(1000L, Instant.ofEpochSecond(1, 0));
    }
}
