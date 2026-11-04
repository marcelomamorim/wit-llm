/*
 * @test
 * @summary Test java.time.Clock.fixed method for null checks and normal behavior
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class ClockWitupTest {
    public static void main(String[] args) {
        testFixedNullInstant();
        testFixedNullZone();
        testFixedNormal();
    }

    private static void testFixedNullInstant() {
        try {
            Clock.fixed(null, ZoneId.of("UTC"));
            throw new RuntimeException("Expected NullPointerException for null fixedInstant");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testFixedNullZone() {
        try {
            Clock.fixed(Instant.EPOCH, null);
            throw new RuntimeException("Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testFixedNormal() {
        Instant instant = Instant.parse("2000-01-01T00:00:00Z");
        ZoneId zone = ZoneId.of("UTC");
        Clock clock = Clock.fixed(instant, zone);
        if (clock == null) {
            throw new RuntimeException("Clock.fixed returned null");
        }
        Instant now = clock.instant();
        if (!instant.equals(now)) {
            throw new RuntimeException("Clock.instant() did not return fixed instant");
        }
        ZoneId clockZone = clock.getZone();
        if (!zone.equals(clockZone)) {
            throw new RuntimeException("Clock.getZone() did not return fixed zone");
        }
    }
}
