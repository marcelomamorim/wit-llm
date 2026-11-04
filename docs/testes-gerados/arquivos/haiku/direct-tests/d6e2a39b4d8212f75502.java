/*
 * @test
 * @summary OffsetDateTime.now(Clock) uses the clock instant and the zone rules offset
 * @run main OffsetDateTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

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

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        try {
            OffsetDateTime.now(null);
            throw new RuntimeException("expected NullPointerException for null clock");
        } catch (NullPointerException expected) {
            // expected
        }

        OffsetDateTime utc = OffsetDateTime.now(new FixedClock(Instant.EPOCH, ZoneOffset.UTC));
        check(utc.equals(OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)),
              "UTC epoch mismatch: " + utc);

        OffsetDateTime plus2 = OffsetDateTime.now(new FixedClock(Instant.EPOCH, ZoneOffset.ofHours(2)));
        check(plus2.equals(OffsetDateTime.of(1970, 1, 1, 2, 0, 0, 0, ZoneOffset.ofHours(2))),
              "+02:00 offset mismatch: " + plus2);
        check(plus2.getOffset().equals(ZoneOffset.ofHours(2)), "offset should be +02:00");

        Instant instant = Instant.ofEpochSecond(1_500_000_000L, 123456789);
        OffsetDateTime negative = OffsetDateTime.now(new FixedClock(instant, ZoneOffset.ofHours(-3)));
        check(negative.toInstant().equals(instant), "instant not preserved: " + negative);
        check(negative.getNano() == 123456789, "nanos not preserved: " + negative);
        check(negative.getOffset().equals(ZoneOffset.ofHours(-3)), "offset should be -03:00");
    }
}
