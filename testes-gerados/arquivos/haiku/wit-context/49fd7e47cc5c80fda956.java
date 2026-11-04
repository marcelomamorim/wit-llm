/*
 * @test
 * @summary Tests OffsetTime.now(Clock) with deterministic clocks and null clock rejection
 * @run main OffsetTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {

    static final class FixedClock extends Clock {
        private final Instant instant;
        private final ZoneId zone;

        FixedClock(Instant instant, ZoneId zone) {
            this.instant = instant;
            this.zone = zone;
        }

        @Override
        public ZoneId getZone() {
            return zone;
        }

        @Override
        public Clock withZone(ZoneId z) {
            return new FixedClock(instant, z);
        }

        @Override
        public Instant instant() {
            return instant;
        }
    }

    public static void main(String[] args) {
        check(OffsetTime.now(new FixedClock(Instant.ofEpochSecond(0), ZoneOffset.ofHours(2)))
                .equals(OffsetTime.of(2, 0, 0, 0, ZoneOffset.ofHours(2))), "epoch at +02:00");

        check(OffsetTime.now(new FixedClock(Instant.ofEpochSecond(3661), ZoneOffset.ofHours(2)))
                .equals(OffsetTime.of(3, 1, 1, 0, ZoneOffset.ofHours(2))), "3661s at +02:00");

        check(OffsetTime.now(new FixedClock(Instant.ofEpochSecond(0), ZoneOffset.ofHours(-5)))
                .equals(OffsetTime.of(19, 0, 0, 0, ZoneOffset.ofHours(-5))), "epoch at -05:00");

        check(OffsetTime.now(new FixedClock(Instant.ofEpochSecond(0, 500), ZoneOffset.UTC))
                .equals(OffsetTime.of(0, 0, 0, 500, ZoneOffset.UTC)), "nanos at UTC");

        try {
            OffsetTime.now(null);
            throw new RuntimeException("expected NullPointerException for null clock");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
