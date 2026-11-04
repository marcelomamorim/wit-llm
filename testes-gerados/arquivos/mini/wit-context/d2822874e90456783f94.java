/*
 * @test
 * @summary Test java.time.Clock.system(ZoneId) for null input and valid zones
 * @run main ClockWitupTest
 */

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

public class ClockWitupTest {
    public static void main(String[] args) {
        testSystemWithNullZone();
        testSystemWithUTCZone();
        testSystemWithNonUTCZone();
    }

    private static void testSystemWithNullZone() {
        try {
            Clock.system(null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException ex) {
            // Expected exception
            if (!"zone".equals(ex.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", ex);
            }
        }
    }

    private static void testSystemWithUTCZone() {
        ZoneId utc = ZoneOffset.UTC;
        Clock clock = Clock.system(utc);
        if (clock == null) {
            throw new AssertionError("Clock.system(UTC) returned null");
        }
        // The returned clock should be the singleton SystemClock.UTC instance or equivalent
        // We cannot access SystemClock.UTC directly, so just check that the zone is UTC
        if (!clock.getZone().equals(utc)) {
            throw new AssertionError("Clock.system(UTC) returned clock with wrong zone");
        }
    }

    private static void testSystemWithNonUTCZone() {
        ZoneId zone = ZoneId.of("Europe/Paris");
        Clock clock = Clock.system(zone);
        if (clock == null) {
            throw new AssertionError("Clock.system(non-UTC) returned null");
        }
        if (!clock.getZone().equals(zone)) {
            throw new AssertionError("Clock.system(non-UTC) returned clock with wrong zone");
        }
    }
}
