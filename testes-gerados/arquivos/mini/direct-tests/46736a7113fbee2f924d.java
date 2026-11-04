/*
 * @test
 * @summary Test java.time.Clock.fixed factory method
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;

public class ClockWitupTest {
    public static void main(String[] args) {
        testFixedClockCreation();
        testFixedClockNullInstant();
        testFixedClockNullZone();
    }

    private static void testFixedClockCreation() {
        Instant fixedInstant = Instant.parse("2020-01-01T00:00:00Z");
        ZoneId zone = ZoneId.of("UTC");
        Clock clock = Clock.fixed(fixedInstant, zone);
        // Verify clock instant and zone
        Instant instantFromClock = clock.instant();
        ZoneId zoneFromClock = clock.getZone();
        if (!fixedInstant.equals(instantFromClock)) {
            throw new AssertionError("Clock.instant() does not return fixed instant");
        }
        if (!zone.equals(zoneFromClock)) {
            throw new AssertionError("Clock.getZone() does not return the given zone");
        }
    }

    private static void testFixedClockNullInstant() {
        ZoneId zone = ZoneId.of("UTC");
        try {
            Clock.fixed(null, zone);
            throw new AssertionError("Expected NullPointerException for null fixedInstant");
        } catch (NullPointerException e) {
            if (!"fixedInstant".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for fixedInstant");
            }
        }
    }

    private static void testFixedClockNullZone() {
        Instant fixedInstant = Instant.parse("2020-01-01T00:00:00Z");
        try {
            Clock.fixed(fixedInstant, null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            if (!"zone".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for zone");
            }
        }
    }
}
