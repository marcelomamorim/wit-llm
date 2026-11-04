/*
 * @test
 * @summary Test the LocalDate.now(Clock) method for correctness and null handling.
 * @run main LocalDateWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testNowWithValidClock();
        testNowWithNullClock();
    }

    static void testNowWithValidClock() {
        ZoneId zone = ZoneId.of("UTC");
        Instant instant = Instant.ofEpochSecond(1609459200L); // 2021-01-01T00:00:00Z
        Clock clock = Clock.fixed(instant, zone);
        LocalDate date = LocalDate.now(clock);
        long daysSinceEpoch = date.toEpochDay();
        long expectedDays = instant.getEpochSecond() / 86400L;
        if (daysSinceEpoch != expectedDays) {
            throw new AssertionError("Expected days: " + expectedDays + ", but got: " + daysSinceEpoch);
        }
    }

    static void testNowWithNullClock() {
        try {
            LocalDate.now(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
