/*
 * @test
 * @summary Test LocalDateTime.now(Clock) for null argument and normal behavior
 * @run main LocalDateTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testNowWithNullClock();
        testNowWithFixedClock();
    }

    private static void testNowWithNullClock() {
        try {
            LocalDateTime.now(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testNowWithFixedClock() {
        Instant fixedInstant = Instant.parse("2018-09-01T10:15:30.123456789Z");
        ZoneOffset offset = ZoneOffset.UTC;
        Clock fixedClock = Clock.fixed(fixedInstant, offset);

        LocalDateTime ldt = LocalDateTime.now(fixedClock);

        // The LocalDateTime should correspond to the fixed instant with the offset applied
        // Since offset is UTC, LocalDateTime should be equal to the instant truncated to nanos
        LocalDateTime expected = LocalDateTime.ofEpochSecond(fixedInstant.getEpochSecond(), fixedInstant.getNano(), offset);

        if (!ldt.equals(expected)) {
            throw new AssertionError("LocalDateTime.now(clock) returned " + ldt + ", expected " + expected);
        }
    }
}
