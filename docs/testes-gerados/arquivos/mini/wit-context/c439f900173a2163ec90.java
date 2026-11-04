/*
 * @test
 * @summary Test Instant.now(Clock) for null argument and normal behavior
 * @run main InstantWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;

public class InstantWitupTest {
    public static void main(String[] args) {
        testNowWithNullClock();
        testNowWithFixedClock();
    }

    private static void testNowWithNullClock() {
        try {
            Instant.now(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException ex) {
            // expected
            if (!"clock".equals(ex.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", ex);
            }
        }
    }

    private static void testNowWithFixedClock() {
        Clock fixedClock = Clock.fixed(Instant.ofEpochSecond(1000L), ZoneId.of("UTC"));
        Instant instant = Instant.now(fixedClock);
        if (!instant.equals(Instant.ofEpochSecond(1000L))) {
            throw new AssertionError("Instant.now(clock) did not return expected instant");
        }
    }
}
