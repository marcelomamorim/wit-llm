/*
 * @test
 * @summary Behavioral tests for Clock.tick(Clock, Duration) validation and tick truncation
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class ClockWitupTest {

    static final class FixedBase extends Clock {
        private final Instant instant;
        private final ZoneId zone;

        FixedBase(Instant instant, ZoneId zone) {
            this.instant = instant;
            this.zone = zone;
        }

        @Override
        public ZoneId getZone() {
            return zone;
        }

        @Override
        public Clock withZone(ZoneId newZone) {
            return new FixedBase(instant, newZone);
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

    static void expectNPE(Clock base, Duration d, String name) {
        try {
            Clock.tick(base, d);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException: " + name);
    }

    static void expectIAE(Duration d, String name) {
        try {
            Clock.tick(new FixedBase(Instant.EPOCH, ZoneOffset.UTC), d);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException: " + name);
    }

    public static void main(String[] args) {
        FixedBase base = new FixedBase(Instant.ofEpochSecond(5, 123_456_789), ZoneOffset.UTC);

        expectNPE(null, Duration.ofMillis(1), "null baseClock");
        expectNPE(base, null, "null tickDuration");

        expectIAE(Duration.ofMillis(-1), "negative duration");
        expectIAE(Duration.ofNanos(3), "non-divisor nanos");

        Clock zero = Clock.tick(base, Duration.ZERO);
        check(zero == base, "zero duration should return baseClock");

        Clock one = Clock.tick(base, Duration.ofNanos(1));
        check(one == base, "1 ns duration should return baseClock");

        Clock millis = Clock.tick(base, Duration.ofMillis(10));
        check(millis != base, "10 ms tick should wrap baseClock");
        check(millis.instant().equals(Instant.ofEpochSecond(5, 120_000_000)),
              "10 ms tick should truncate to 5.120s but was " + millis.instant());
        check(millis.getZone().equals(ZoneOffset.UTC), "tick clock should keep base zone");

        Clock second = Clock.tick(base, Duration.ofSeconds(1));
        check(second != base, "1 s tick should wrap baseClock");
        check(second.instant().equals(Instant.ofEpochSecond(5)),
              "1 s tick should truncate to 5s but was " + second.instant());

        Clock sub = Clock.tick(new FixedBase(Instant.ofEpochSecond(1, 999_999_999), ZoneOffset.UTC),
                               Duration.ofNanos(250));
        check(sub.instant().equals(Instant.ofEpochSecond(1, 999_999_750)),
              "250 ns tick should truncate to 1.999999750 but was " + sub.instant());
    }
}
