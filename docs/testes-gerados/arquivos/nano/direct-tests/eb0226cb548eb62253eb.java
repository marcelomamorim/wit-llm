/* @test    @summary Test the millis() method of java.time.Clock    @run main ClockWitupTest */

import java.time.Clock;
import java.time.Instant;

public class ClockWitupTest {

    public static void main(String[] args) {
        testMillisReturnsEpochMillis();
    }

    public static void testMillisReturnsEpochMillis() {
        // Create a Clock fixed at a specific instant
        Instant fixedInstant = Instant.ofEpochMilli(1625097600000L); // 2021-07-01T00:00:00Z
        Clock fixedClock = Clock.fixed(fixedInstant, java.time.ZoneOffset.UTC);
        long millis = fixedClock.millis();
        assert millis == 1625097600000L : "Expected 1625097600000L, but got " + millis;

        // Create a Clock offset by a certain duration
        Instant nowInstant = Instant.now();
        Clock offsetClock = Clock.offset(Clock.systemUTC(), java.time.Duration.ofSeconds(10));
        long offsetMillis = offsetClock.millis();
        long expectedMillis = nowInstant.toEpochMilli() + 10000;
        // Allow small difference due to execution time
        long delta = Math.abs(offsetMillis - expectedMillis);
        assert delta < 50 : "Offset millis difference too large: " + delta;
    }
}
