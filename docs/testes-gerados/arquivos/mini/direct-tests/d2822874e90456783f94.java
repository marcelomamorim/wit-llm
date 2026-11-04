/*
 * @test
 * @summary Test java.time.Clock.system(ZoneId) factory method
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
        } catch (NullPointerException e) {
            // expected
            if (!"zone".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", e);
            }
        }
    }

    private static void testSystemWithUTCZone() {
        Clock c = Clock.system(ZoneOffset.UTC);
        if (c == null) {
            throw new AssertionError("Clock.system(ZoneOffset.UTC) returned null");
        }
        // The returned clock should be the constant SystemClock.UTC instance
        // but since SystemClock is package-private, we check that repeated calls return the same instance
        Clock c2 = Clock.system(ZoneOffset.UTC);
        if (c != c2) {
            throw new AssertionError("Clock.system(ZoneOffset.UTC) did not return the same instance");
        }
    }

    private static void testSystemWithNonUTCZone() {
        ZoneId zone = ZoneId.of("Europe/Paris");
        Clock c = Clock.system(zone);
        if (c == null) {
            throw new AssertionError("Clock.system(non-UTC zone) returned null");
        }
        // The returned clock should not be the same as the UTC clock
        Clock utcClock = Clock.system(ZoneOffset.UTC);
        if (c == utcClock) {
            throw new AssertionError("Clock.system(non-UTC zone) returned the UTC clock instance");
        }
        // The zone of the returned clock should be the requested zone
        if (!zone.equals(c.getZone())) {
            throw new AssertionError("Clock.system(zone) returned clock with wrong zone");
        }
    }
}
