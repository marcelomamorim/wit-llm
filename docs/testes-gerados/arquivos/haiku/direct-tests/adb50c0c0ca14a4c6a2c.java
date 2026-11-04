/*
 * @test
 * @summary Verifies Clock.millis() returns the epoch milliseconds of the clock's instant
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

public class ClockWitupTest {

    public static void main(String[] args) {
        checkMillis(Instant.ofEpochMilli(1234567L), 1234567L);
        checkMillis(Instant.ofEpochMilli(0L), 0L);
        checkMillis(Instant.ofEpochMilli(-1L), -1L);
        checkMillis(Instant.ofEpochSecond(1, 999_999_999L), 1999L);
        checkMillis(Instant.ofEpochSecond(-1, 500_000_000L), -500L);
        checkMillis(Instant.ofEpochSecond(1_000_000L), 1_000_000_000L);

        Instant base = Instant.ofEpochMilli(42_000L);
        Clock clock = Clock.fixed(base, ZoneOffset.UTC);
        long first = clock.millis();
        long second = clock.millis();
        if (first != 42_000L || second != 42_000L) {
            throw new RuntimeException("fixed clock millis not stable: " + first + ", " + second);
        }
    }

    private static void checkMillis(Instant instant, long expected) {
        Clock clock = Clock.fixed(instant, ZoneOffset.UTC);
        long actual = clock.millis();
        if (actual != expected) {
            throw new RuntimeException("Expected " + expected + " but got " + actual
                    + " for instant " + instant);
        }
    }
}
