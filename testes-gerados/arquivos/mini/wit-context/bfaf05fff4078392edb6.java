/*
 * @test
 * @summary Test java.time.LocalTime.now(Clock) method for null argument and normal behavior
 * @run main java.time.LocalTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Objects;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testNowWithNullClock();
        testNowWithFixedClock();
    }

    private static void testNowWithNullClock() {
        try {
            LocalTime.now(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException ex) {
            // Expected exception
            if (!"clock".equals(ex.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", ex);
            }
        }
    }

    private static void testNowWithFixedClock() {
        Instant fixedInstant = Instant.parse("2018-09-01T10:15:30.00Z");
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneId.of("UTC"));
        LocalTime time = LocalTime.now(fixedClock);
        // The expected time is 10:15:30
        if (time.getHour() != 10 || time.getMinute() != 15 || time.getSecond() != 30) {
            throw new AssertionError("LocalTime.now(clock) returned unexpected time: " + time);
        }
    }
}
