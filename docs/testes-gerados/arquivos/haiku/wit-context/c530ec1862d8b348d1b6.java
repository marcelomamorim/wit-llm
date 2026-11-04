/*
 * @test
 * @summary Witup-generated behavioural tests for Clock.offset null guards, zero-offset shortcut and shifted instant
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

public class ClockWitupTest {

    public static void main(String[] args) {
        Clock base = Clock.fixed(Instant.ofEpochSecond(1000), ZoneOffset.UTC);

        expectNullPointerException(() -> Clock.offset(null, Duration.ofSeconds(1)));
        expectNullPointerException(() -> Clock.offset(base, null));
        expectNullPointerException(() -> Clock.offset(null, null));

        Clock same = Clock.offset(base, Duration.ZERO);
        if (same != base) {
            throw new RuntimeException("zero offset should return the same base clock instance");
        }

        Clock shifted = Clock.offset(base, Duration.ofSeconds(5));
        if (shifted == base) {
            throw new RuntimeException("non-zero offset should not return the base clock instance");
        }
        if (!shifted.instant().equals(Instant.ofEpochSecond(1005))) {
            throw new RuntimeException("offset clock instant should be base instant plus offset");
        }
        if (!base.instant().equals(Instant.ofEpochSecond(1000))) {
            throw new RuntimeException("base clock must not be modified by offset");
        }

        Clock negative = Clock.offset(base, Duration.ofSeconds(-10));
        if (!negative.instant().equals(Instant.ofEpochSecond(990))) {
            throw new RuntimeException("negative offset should move the instant backwards");
        }
    }

    private static void expectNullPointerException(Runnable action) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException was not thrown");
    }
}
